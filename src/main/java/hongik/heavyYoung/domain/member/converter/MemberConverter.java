package hongik.heavyYoung.domain.member.converter;

import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;

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

    public static MemberResponseDTO.MyPageInfo toMyPageInfo(
            LockerResponse.MyLockerInfoDTO locker,
            List<RentalResponseDTO.RentalHistory> items,
            boolean isStudentFeePaid,
            MemberResponseDTO.BlacklistInfo blacklist
    ) {
        return MemberResponseDTO.MyPageInfo.builder()
                .locker(locker)
                .items(items)
                .isStudentFeePaid(isStudentFeePaid)
                .blacklist(blacklist)
                .build();
    }
}
