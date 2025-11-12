package hongik.heavyYoung.domain.member.service;

import hongik.heavyYoung.domain.member.converter.AuthConverter;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthResponseDTO;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.GeneralException;
import hongik.heavyYoung.global.exception.customException.AuthException;
import hongik.heavyYoung.global.jwt.JwtProvider;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // == 회원 가입 == //
    @Transactional
    public AuthResponseDTO.AuthSignUpResponseDTO signUp(AuthRequestDTO.AuthSignUpRequestDTO authRequestDTO) {
        // 학교 이메일인지 검증
//        if(authRequestDTO.getEmail() == null || isSchoolEmail(authRequestDTO.getEmail())) {
//            throw new GeneralException(ErrorStatus.INVALID_EMAIL);
//        }

        // 이미 회원인지 검증
        if(memberRepository.existsByEmail(authRequestDTO.getEmail())) {
            throw new GeneralException(ErrorStatus.MEMBER_ALREADY_EXIST);
        }
        // 비밀번호가 일치하는지 검증
        if(!(authRequestDTO.getPassword()).equals(authRequestDTO.getPasswordConfirm())) {
            throw new GeneralException(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        String encodedPassword = passwordEncoder.encode(authRequestDTO.getPassword());

        // 멤버 추가
        Member member = AuthConverter.toMemberEntity(authRequestDTO, encodedPassword);
        memberRepository.save(member);

        return  AuthConverter.toAuthSignUpResponseDTO(member);
    }

    // == 로그인 == //
    @Transactional(readOnly = true)
    public AuthResponseDTO.AuthLoginResponseDTO login(AuthRequestDTO.AuthLoginInRequestDTO req) {
        Member m = memberRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new AuthException(ErrorStatus.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(req.getPassword(), m.getPassword())) {
            throw new AuthException(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        // accessToken, refreshToken 둘 다 발급
        String accessToken = jwtProvider.createAccessToken(m.getId(), m.getEmail(), m.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(m.getId(), m.getEmail());

        long accessExp = jwtProvider.getAccessTokenValiditySeconds();
        long refreshExp = jwtProvider.getRefreshTokenValiditySeconds();

        return AuthConverter.toAuthLoginResponseDTO(m, accessToken, refreshToken, accessExp, refreshExp);
    }

    private boolean isSchoolEmail(String email) {
        if(email == null) {
            return false;
        }
        String e = email.trim().toLowerCase(Locale.ROOT);
        if (!e.endsWith("@g.hongik.ac.kr")) return false;
        int at = e.lastIndexOf('@');
        return at > 0;
    }

    // == 로그아웃 == //
    @Transactional
    public void logout(Long memberId) {
        // redis 설정 이후 추가
        // jwt 토큰 기반이기에 백엔드에서 처리할게 없음
    }
}
