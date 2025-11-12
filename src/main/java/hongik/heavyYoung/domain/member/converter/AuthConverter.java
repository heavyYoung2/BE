package hongik.heavyYoung.domain.member.converter;

import hongik.heavyYoung.domain.member.dto.authDTO.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthResponseDTO;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.MemberRole;
import hongik.heavyYoung.domain.member.enums.MemberStatus;

public class AuthConverter {

    public static Member toMemberEntity(AuthRequestDTO.AuthSignUpRequestDTO dto,  String encodedPassword) {
        return Member.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .role(MemberRole.USER)
                .memberStatus(MemberStatus.ACTIVE)
                .phoneNumber(dto.getPhoneNumber())
                .studentId(dto.getStudentId())
                .studentName(dto.getStudentName())
                .build();
    }

    public static AuthResponseDTO.SignUpResponseDTO toSignUpResponseDTO(Member member) {
        return AuthResponseDTO.SignUpResponseDTO.builder()
                .memberId(member.getId()).build();
    }

    public static AuthResponseDTO.LoginResponseDTO toLoginResponseDTO(
            Member member, String accessToken, String refreshToken, long accessExp, long refreshExp) {
        return AuthResponseDTO.LoginResponseDTO.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .role(member.getRole())
                .status(member.getMemberStatus())
                .studentId(member.getStudentId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(accessExp)
                .refreshExpiresIn(refreshExp)
                .build();
    }

    public static AuthResponseDTO.SendCodeResponseDTO toSendCodeResponseDTO(String code) {
        return AuthResponseDTO.SendCodeResponseDTO.builder()
                .code(code).build();
    }

    public static AuthResponseDTO.VerifyCodeResponseDTO toVerifyCodeResponseDTO(String email) {
        return AuthResponseDTO.VerifyCodeResponseDTO.builder()
                .email(email)
                .message("이메일 인증이 완료되었습니다.")
                .build();
    }

    public static AuthResponseDTO.TempPasswordResponseDTO toTempPasswordResponseDTO(String email) {
        return AuthResponseDTO.TempPasswordResponseDTO.builder()
                .email(email)
                .message("임시 비밀번호가 발급되었습니다. 이메일을 확인하세요.")
                .build();
    }
}
