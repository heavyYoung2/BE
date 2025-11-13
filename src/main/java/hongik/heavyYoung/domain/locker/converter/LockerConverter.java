package hongik.heavyYoung.domain.locker.converter;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.entity.MemberApplication;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.locker.dto.LockerRequest;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.member.entity.Member;

public class LockerConverter {

    // 새로운 사물함 신청 변환
    public static Application toLockerApplication(LockerRequest.LockerApplicationAddRequestDTO lockerApplicationAddRequestDTO, int lockerApplicationCanCount) {
        return Application.builder()
                .applicationCanCount(lockerApplicationCanCount)
                .applicationStartAt(lockerApplicationAddRequestDTO.getApplicationStartAt())
                .applicationEndAt(lockerApplicationAddRequestDTO.getApplicationEndAt())
                .applicationSemester(lockerApplicationAddRequestDTO.getSemester())
                .applicationType(lockerApplicationAddRequestDTO.getApplicationType())
                .canAssign(lockerApplicationAddRequestDTO.getApplicationType() != ApplicationType.LOCKER_ADDITIONAL)
                .build();
    }

    // 사물함 본 신청을 배정으로 변환
    public static LockerAssignment toLockerAssignmentForLockerMain(MemberApplication memberApplication, Locker locker, String semester) {
        return LockerAssignment.builder()
                .member(memberApplication.getMember())
                .locker(locker)
                .assignSemester(semester)
                .isCurrentSemester(true)
                .build();
    }

    // 사물함 추가 신청을 배정으로 변환
    public static LockerAssignment toLockerAssignmentForLockerAdditional(Member member, Locker locker, String semester) {
        return LockerAssignment.builder()
                .member(member)
                .locker(locker)
                .assignSemester(semester)
                .build();
    }

    // 사물함 신청 내역 변환
    public static MemberApplication toMemberApplication(Application lockerApplication, Member member){
        return MemberApplication.builder()
                .application(lockerApplication)
                .member(member)
                .applicationSemester(lockerApplication.getApplicationSemester())
                .build();
    }
}
