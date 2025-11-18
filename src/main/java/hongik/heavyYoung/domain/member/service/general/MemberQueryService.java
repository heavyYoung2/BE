package hongik.heavyYoung.domain.member.service.general;

import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;

public interface MemberQueryService {
    MemberResponseDTO.BlacklistInfo getBlacklist(Long authMemberId);
    MemberResponseDTO.MyPageInfo findMyPage(Long authMemberId);
}
