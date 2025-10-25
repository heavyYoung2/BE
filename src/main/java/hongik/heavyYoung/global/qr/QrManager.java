package hongik.heavyYoung.global.qr;

import hongik.heavyYoung.global.jwt.JwtProvider;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.QrException;
import hongik.heavyYoung.global.qr.payload.QrPayload;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class QrManager {
    private final QrPayloadFactory qrPayloadFactory;
    private final JwtProvider jwtProvider;

    /**
     * qrToken 생성
     */
    public String generateQrToken(QrType qrType, Map<String, Object> context) {

        // QrPayloadFactory를 통해, qrType에 맞는 qrPayload 생성
        QrPayload qrPayload = qrPayloadFactory.create(qrType, context);

        // qrPayload -> JWT 서명/발급
        String qrToken = jwtProvider.generateQrToken(qrPayload);

        return qrToken;
    }

    /**
     * qr정보 가져오기
     */
    public QrPayload decodeQrToken(QrType qrType, String qrToken) {

        // 토큰 파싱
        Claims claims = jwtProvider.getClaims(qrToken);

        // 페이로드 생성
        QrPayload qrPayload = qrPayloadFactory.createFromClaims(qrType, claims);

        // QR 타입 검증
        if (qrPayload.getQrType() != qrType) {
            throw new QrException(ErrorStatus.QR_AUTH_TYPE_MISMATCH);
        }

        return qrPayload;
    }

}
