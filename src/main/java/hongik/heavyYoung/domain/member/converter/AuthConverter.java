package hongik.heavyYoung.domain.member.converter;

import hongik.heavyYoung.domain.member.dto.authDTO.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthResponseDTO;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.MemberRole;
import hongik.heavyYoung.domain.member.enums.MemberStatus;

public class AuthConverter {

    public static Member toMemberEntity(AuthRequestDTO.AuthSignUpRequestDTO dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(MemberRole.USER)
                .memberStatus(MemberStatus.ACTIVE)
                .build();
    }

    public static AuthResponseDTO.AuthSignUpResponseDTO toAuthSignUpResponseDTO(Member member) {
        return AuthResponseDTO.AuthSignUpResponseDTO.builder()
                .memberId(member.getId()).build();
    }
}
