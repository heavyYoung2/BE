package hongik.heavyYoung.domain.member.service.general.impl;

import hongik.heavyYoung.domain.member.converter.AuthConverter;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthResponseDTO;
import hongik.heavyYoung.domain.member.entity.EmailVerify;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.EmailVerifyRepository;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.GeneralException;
import hongik.heavyYoung.global.exception.customException.AuthException;
import hongik.heavyYoung.global.jwt.JwtProvider;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final MailService mailService;
    private final EmailVerifyRepository emailVerifyRepository;

    // == 회원 가입 == //
    @Transactional
    public AuthResponseDTO.SignUpResponseDTO signUp(AuthRequestDTO.AuthSignUpRequestDTO authRequestDTO) {

        String email = authRequestDTO.getEmail();

        // 학교 이메일인지 검증
        if(!isSchoolEmail(email)) {
            throw new AuthException(ErrorStatus.INVALID_EMAIL);
        }

        // 이미 회원인지 검증
        if(memberRepository.existsByEmail(email)) {
            throw new AuthException(ErrorStatus.MEMBER_ALREADY_EXIST);
        }

        // 이메일 인증 선행 여부 검증
        EmailVerify emailVerify = emailVerifyRepository.findByEmailAddress(email)
                .orElseThrow(() -> new AuthException(ErrorStatus.EMAIL_NOT_FOUND));
        if (!emailVerify.isVerified()) {
            throw new GeneralException(ErrorStatus.EMAIL_NOT_VERIFIED);
        }

        // 비밀번호와 비밀번호 확인이 일치하는지 검증
        if(!(authRequestDTO.getPassword()).equals(authRequestDTO.getPasswordConfirm())) {
            throw new AuthException(ErrorStatus.PASSWORD_CONFIRM_NOT_MATCH);
        }



        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(authRequestDTO.getPassword());

        // 멤버 추가
        Member member = AuthConverter.toMemberEntity(authRequestDTO, encodedPassword);
        memberRepository.save(member);

        // 가입 성공시 EmailVerify에 해당 이메일 정리
        emailVerifyRepository.deleteByEmailAddress(email);

        return  AuthConverter.toSignUpResponseDTO(member);
    }

    // == 로그인 == //
    @Transactional(readOnly = true)
    public AuthResponseDTO.LoginResponseDTO login(AuthRequestDTO.AuthLoginRequestDTO req) {
        Member m = memberRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new AuthException(ErrorStatus.MEMBER_NOT_FOUND));

        if(!isSchoolEmail(req.getEmail())) {
            throw  new AuthException(ErrorStatus.INVALID_EMAIL);
        }
        if (!passwordEncoder.matches(req.getPassword(), m.getPassword())) {
            throw new AuthException(ErrorStatus.INVALID_PASSWORD);
        }

        // accessToken, refreshToken 둘 다 발급
        String accessToken = jwtProvider.createAccessToken(m.getId(), m.getEmail(), m.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(m.getId(), m.getEmail());

        long accessExp = jwtProvider.getAccessTokenValiditySeconds();
        long refreshExp = jwtProvider.getRefreshTokenValiditySeconds();

        return AuthConverter.toLoginResponseDTO(m, accessToken, refreshToken, accessExp, refreshExp);
    }

    // == 로그아웃 == //
    @Transactional
    public void logout(Long memberId) {
        // redis 설정 이후 추가
        // jwt 토큰 기반이기에 백엔드에서 처리할게 없음
    }

    @Transactional
    public AuthResponseDTO.SendCodeResponseDTO issueSchoolEmailCode(AuthRequestDTO.SendCodeRequestDTO dto) {
        // 학교 이메일인지 검증
        String email = dto.getEmail();
        if (!isSchoolEmail(email)) {
            throw new GeneralException(ErrorStatus.INVALID_EMAIL);
        }

        // 회원인지 검증
        if(memberRepository.existsByEmail(email)) {
            throw new AuthException(ErrorStatus.MEMBER_ALREADY_EXIST);
        }

        // 여기서 인증 코드 생성
        String code = String.format("%06d", new SecureRandom().nextInt(1_000_000));

        // 이메일 엔티티에 저장
        EmailVerify emailEntity = emailVerifyRepository.findByEmailAddress(email)
                .map(existing -> {      // 이미 해당 이메일이 존재하면
                    existing.updateCode(code);
                    return existing;
                })
                .orElseGet(() -> EmailVerify.builder()
                        .emailAddress(email)
                        .code(code)
                        .verified(false)
                        .build());
        emailVerifyRepository.save(emailEntity);

        // 이메일 발송
        mailService.sendVerificationCode(email, code);

        return AuthConverter.toSendCodeResponseDTO(code);
    }


    @Transactional
    public AuthResponseDTO.VerifyCodeResponseDTO verifySchoolEmailCode(AuthRequestDTO.VerifyCodeRequestDTO dto) {
        // 여기서 전송 누른 이메일 주소 찾기
        EmailVerify emailEntity = emailVerifyRepository.findByEmailAddress(dto.getEmail())
                .orElseThrow(() -> new GeneralException(ErrorStatus.EMAIL_NOT_FOUND));

        // 여기서 해당 이메일과 코드가 일치하는지 검사
        if(!emailEntity.getCode().equals(dto.getCode())) {
            throw new GeneralException(ErrorStatus.EMAIL_CODE_MISMATCH);
        }

        // 완료 되었다고 엔티티 수정
        emailEntity.updateVerified(true);

        return AuthConverter.toVerifyCodeResponseDTO(emailEntity.getEmailAddress());
    }

    @Transactional
    public AuthResponseDTO.TempPasswordResponseDTO issueTemporaryPassword(String email) {
        // 회원 존재 하는지 확인
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(ErrorStatus.MEMBER_NOT_FOUND));

        // 임시 비밀번호 생성 (12자리, 영문+숫자+특수문자)
        String tempPassword = generateTempPassword(12);

        // 비밀번호 암호화 후 DB 업데이트
        String encodedPassword = passwordEncoder.encode(tempPassword);
        member.updatePassword(encodedPassword);

        // 이메일 발송
        mailService.sendTemporaryPassword(email, tempPassword);

        // 응답 DTO 반환
        return AuthConverter.toTempPasswordResponseDTO(email);
    }

    @Transactional
    public AuthResponseDTO.ChangePasswordResponseDTO changePassword(Long authMemberId, AuthRequestDTO.ChangePasswordRequestDTO dto) {
        Member member = memberRepository.findById(authMemberId).orElseThrow(() -> new AuthException(ErrorStatus.MEMBER_NOT_FOUND));

        String originPassword = passwordEncoder.encode(member.getPassword());

        if(!originPassword.equals(dto.getPassword())) {
            throw new AuthException(ErrorStatus.INVALID_PASSWORD);
        }

        if(!(dto.getNewPassword().equals(dto.getNewPasswordConfirm()))) {
            throw new AuthException(ErrorStatus.PASSWORD_CONFIRM_NOT_MATCH);
        }

        member.updatePassword(passwordEncoder.encode(dto.getNewPassword()));

        return AuthConverter.toChangePasswordResponseDTO(member);
    }

    private String generateTempPassword(int length) {
        final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }


    private boolean isSchoolEmail(String email) {
        return email.endsWith("@g.hongik.ac.kr") || email.endsWith("@mail.hongik.ac.kr");
    }

    public AuthResponseDTO.SendCodeResponseDTO issuePasswordVerifyCode(AuthRequestDTO.SendCodeRequestDTO dto) {


    }
}
