package hongik.heavyYoung.domain.member.service.general;

import hongik.heavyYoung.domain.member.dto.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.AuthResponseDTO;

public interface MemberCommandService {
    AuthResponseDTO.ChangePasswordResponseDTO changePassword(Long authMemberId, AuthRequestDTO.ChangePasswordRequestDTO dto);
}
