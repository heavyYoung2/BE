package hongik.heavyYoung.domain.member.service.general;

import hongik.heavyYoung.domain.member.dto.authDTO.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthResponseDTO;

public interface MemberCommandService {
    AuthResponseDTO.ChangePasswordResponseDTO changePassword(Long authMemberId, AuthRequestDTO.ChangePasswordRequestDTO dto);
}
