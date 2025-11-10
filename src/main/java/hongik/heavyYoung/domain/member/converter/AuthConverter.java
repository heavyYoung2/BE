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

    public static AuthResponseDTO.AuthSignUpResponseDTO toAuthSignUpResponseDTO(Member member) {
        return AuthResponseDTO.AuthSignUpResponseDTO.builder()
                .memberId(member.getId()).build();
    }

    public static AuthResponseDTO.AuthLoginResponseDTO toAuthLoginResponseDTO(
            Member member, String accessToken, String refreshToken, long accessExp, long refreshExp) {
        return AuthResponseDTO.AuthLoginResponseDTO.builder()
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
}
