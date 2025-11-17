package hongik.heavyYoung.domain.member.service.admin.impl;

import hongik.heavyYoung.domain.member.converter.MemberConverter;
import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.MemberRole;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.domain.member.service.admin.AdminMemberCommandService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminMemberCommandServiceImpl implements AdminMemberCommandService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDTO.StudentCouncilCandidateInfo createStudentCouncil(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        if (member.getRole() != MemberRole.USER ) {
            throw new MemberException(ErrorStatus.MEMBER_ALREADY_COUNCIL_MEMBER);
        }

        member.updateRoleToAdmin();
        return MemberConverter.toStudentCouncilCandidateInfo(member);
    }

    @Override
    public void deleteStudentCouncil(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        if (member.getRole() == MemberRole.USER) {
            throw new MemberException(ErrorStatus.MEMBER_NOT_STUDENT_COUNCIL);
        }

        if (member.getRole() == MemberRole.OWNER) {
            throw new MemberException(ErrorStatus.CANNOT_DELETE_OWNER);
        }

        member.updateRoleToUser();
    }
}
