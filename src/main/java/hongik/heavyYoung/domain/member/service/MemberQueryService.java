package hongik.heavyYoung.domain.member.service;

import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;

public interface MemberQueryService {
    MemberResponseDTO.BlacklistInfo getBlacklist(Long authMemberId);
}
