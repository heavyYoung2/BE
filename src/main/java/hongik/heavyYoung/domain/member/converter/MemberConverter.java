package hongik.heavyYoung.domain.member.converter;

import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;
import hongik.heavyYoung.domain.member.entity.Member;

import java.time.LocalDate;
import java.util.List;

public class MemberConverter {

    public static MemberResponseDTO.BlacklistInfo toBlacklistInfo(
            boolean blacklisted,
            LocalDate blacklistUntil) {

        return MemberResponseDTO.BlacklistInfo.builder()
                .blacklisted(blacklisted)
                .blacklistUntil(blacklistUntil)
                .build();
    }

    public static MemberResponseDTO.StudentCouncilInfo toStudentCouncilInfo(List<Member> studentCouncilMembers) {
        List<MemberResponseDTO.StudentCouncilInfo.StudentCouncilMemberInfo> adminMemberInfoList =
                studentCouncilMembers.stream()
                        .map(member -> MemberResponseDTO.StudentCouncilInfo.StudentCouncilMemberInfo.builder()
                                .memberId(member.getId())
                                .studentId(member.getStudentId())
                                .studentName(member.getStudentName())
                                .build()
                        )
                        .toList();

        return MemberResponseDTO.StudentCouncilInfo.builder()
                .studentCouncilMembers(adminMemberInfoList)
                .build();
    }

    public static MemberResponseDTO.StudentCouncilCandidateInfo toStudentCouncilCandidateInfo(Member member){
        return MemberResponseDTO.StudentCouncilCandidateInfo.builder()
                .memberId(member.getId())
                .studentId(member.getStudentId())
                .studentName(member.getStudentName())
                .build();
    }
}
