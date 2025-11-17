package hongik.heavyYoung.domain.member.service.admin;

import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;

public interface AdminMemberCommandService {
    MemberResponseDTO.StudentCouncilCandidateInfo createStudentCouncil(Long memberId);
    void deleteStudentCouncil(Long memberId);
}
