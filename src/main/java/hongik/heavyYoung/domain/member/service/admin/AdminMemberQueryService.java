package hongik.heavyYoung.domain.member.service.admin;

import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;

public interface AdminMemberQueryService {
    MemberResponseDTO.StudentCouncilInfo findStudentCouncil();
    MemberResponseDTO.StudentCouncilCandidateInfo findStudentCouncilCandidate(String studentId);
}
