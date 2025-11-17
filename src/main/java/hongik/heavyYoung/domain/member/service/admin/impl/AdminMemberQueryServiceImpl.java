package hongik.heavyYoung.domain.member.service.admin.impl;

import hongik.heavyYoung.domain.member.converter.MemberConverter;
import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.MemberRole;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.domain.member.service.admin.AdminMemberQueryService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminMemberQueryServiceImpl implements AdminMemberQueryService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDTO.StudentCouncilInfo findStudentCouncil() {
        List<MemberRole> roles = List.of(MemberRole.ADMIN, MemberRole.OWNER);
        List<Member> studentCouncilMembers = memberRepository.findAllByRoleIn(roles);
        return MemberConverter.toStudentCouncilInfo(studentCouncilMembers);
    }

    @Override
    public MemberResponseDTO.StudentCouncilCandidateInfo findStudentCouncilCandidate(String studentId) {
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
        return MemberConverter.toStudentCouncilCandidateInfo(member);
    }
}
