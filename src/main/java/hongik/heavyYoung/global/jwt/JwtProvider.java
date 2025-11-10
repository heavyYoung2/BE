package hongik.heavyYoung.global.jwt;

import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.GeneralException;
import hongik.heavyYoung.global.exception.customException.AuthException;
import hongik.heavyYoung.global.exception.customException.QrException;
import hongik.heavyYoung.global.qr.payload.QrPayload;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${jwt.qr.secret}")
    private String secret; // Base64 난수 문자열
    @Value("${jwt.qr.exp}")
    private long expiration; // 1분 (60000ms)

    private SecretKey key;
    private JwtParser jwtParser;

    // == AUTH == //
    @Value("${jwt.auth.secret}")
    private String authSecretBase64;

    @Value("${jwt.auth.access-exp-seconds}")
    private long accessExpSeconds;

    @Value("${jwt.auth.refresh-exp-seconds}")
    private long refreshExpSeconds;

    private SecretKey authKey;
    private JwtParser authParser;


    // 시크릿 키 생성
    @PostConstruct
    public void initSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parser().verifyWith(this.key).build();

        this.authKey = Keys.hmacShaKeyFor(keyBytes);
        this.authParser = Jwts.parser().verifyWith(this.authKey).build();
    }

    // QR용 토큰 생성
    public String generateQrToken(QrPayload qrPayload) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration * 100000); // TODO : 개발 이후 1분으로 변경
        Map<String, Object> map = qrPayload.toMap();
        String subject = map.get("memberId").toString();

        return Jwts.builder()
                .subject(subject)               // 발행 대상 (memberId)
                .issuedAt(now)                  // 발행 시간
                .expiration(exp)                // 만료 시간
                .claims(map)                    // 클레임
                .signWith(key)                  // 서명에 사용할 키
                .compact();                     // JWT 생성 및 직렬화
    }

    // 클레임 추출
    public Claims getClaims(String token) {
        try {
            return jwtParser.
                    parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException e) {
            throw new QrException(ErrorStatus.QR_AUTH_INVALID_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new QrException(ErrorStatus.QR_AUTH_EXPIRED);
        } catch (MalformedJwtException e) {
            throw new QrException(ErrorStatus.QR_AUTH_INVALID_FORMAT);
        }
    }

    public String createAccessToken(Long memberId, String email, String role) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessExpSeconds);

        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim("email", email)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(authKey)
                .compact();
    }

    public String createRefreshToken(Long memberId, String email) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(refreshExpSeconds);

        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim("email", email)
                .claim("type", "refresh")
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(authKey)
                .compact();
    }

    /** 인증 토큰(claims) 파싱 */
    public Claims parseAuthClaims(String token) {
        try {
            return authParser.parseSignedClaims(token).getPayload();
        } catch (SignatureException e) {
            throw new AuthException(ErrorStatus.INVALID_JWT_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new AuthException(ErrorStatus.JWT_EXPIRED);
        } catch (MalformedJwtException | IllegalArgumentException e) {
            throw new AuthException(ErrorStatus.INVALID_JWT_FORMAT);
        }
    }

    public long getAccessTokenValiditySeconds() {
        return accessExpSeconds;
    }

    public long getRefreshTokenValiditySeconds() {
        return refreshExpSeconds;
    }
}
