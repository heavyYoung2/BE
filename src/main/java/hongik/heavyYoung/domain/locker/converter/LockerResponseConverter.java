package hongik.heavyYoung.domain.locker.converter;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.entity.MemberApplication;
import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.enums.LockerRentalStatus;
import hongik.heavyYoung.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public class LockerResponseConverter {

    // 사물함 기본 정보
    public static LockerResponse.LockerInfoDTO toLockerInfoDTO(Locker locker, Member member, String lockerStatus) {
        return LockerResponse.LockerInfoDTO.builder()
                .lockerId(locker.getId())
                .lockerNumber(locker.getLockerSection() + locker.getLockerNumber())
                .lockerStatus(lockerStatus)
                .studentId(member != null ? member.getStudentId() : null)
                .studentName(member != null ? member.getStudentName() : null)
                .build();
    }

    // 나의 사물함 정보 (사물함 배정 o)
    public static LockerResponse.MyLockerInfoDTO toMyLockerInfoDTO(Locker locker, LockerRentalStatus lockerRentalStatus) {
        return LockerResponse.MyLockerInfoDTO.builder()
                .lockerId(locker.getId())
                .lockerNumber(locker.getLockerSection() + locker.getLockerNumber())
                .lockerRentalStatus(lockerRentalStatus.name())
                .build();
    }

    // 나의 사물함 정보 (사물함 배정 x)
    public static LockerResponse.MyLockerInfoDTO toMyLockerInfoDTO(LockerRentalStatus lockerRentalStatus) {
        return LockerResponse.MyLockerInfoDTO.builder()
                .lockerRentalStatus(lockerRentalStatus.name())
                .build();
    }


    // 사물함 신청 정보
    public static LockerResponse.LockerApplicationInfoDTO toLockerApplicationInfoDTO(Application lockerApplication) {
        boolean isInTimeRange = !LocalDateTime.now().isBefore(lockerApplication.getApplicationStartAt()) && !LocalDateTime.now().isAfter(lockerApplication.getApplicationEndAt());
        boolean hasCapacity = lockerApplication.getApplicationMemberCount() < lockerApplication.getApplicationCanCount();
        boolean canApply = isInTimeRange && hasCapacity;

        return LockerResponse.LockerApplicationInfoDTO.builder()
                .applicationId(lockerApplication.getId())
                .applicationStartAt(lockerApplication.getApplicationStartAt())
                .applicationEndAt(lockerApplication.getApplicationEndAt())
                .semester(lockerApplication.getApplicationSemester())
                .applicationType(lockerApplication.getApplicationType().name())
                .canApply(canApply)
                .canAssign(lockerApplication.isCanAssign())
                .build();
    }

    // 사물함 신청 정보 리스트
    public static List<LockerResponse.LockerApplicationInfoDTO> toLockerApplicationInfoDTOList(List<Application> applications) {
        return applications.stream()
                .map(LockerResponseConverter::toLockerApplicationInfoDTO)
                .toList();
    }

    // 사물함 신청 상세 정보
    public static LockerResponse.LockerApplicationDetailInfoDTO toLockerApplicationDetailInfoDTO(
            Application lockerApplication,
            List<MemberApplication> memberApplications
    ) {
        List<LockerResponse.LockerApplicationDetailInfoDTO.ApplicantInfoDTO> applicantDTOs =
                memberApplications.stream()
                        .map(memberApplication -> {
                            Member member = memberApplication.getMember();
                            return LockerResponse.LockerApplicationDetailInfoDTO.ApplicantInfoDTO.builder()
                                    .studentId(member.getStudentId())
                                    .studentName(member.getStudentName())
                                    .appliedAt(memberApplication.getCreatedAt())
                                    .build();
                        })
                        .toList();

        return LockerResponse.LockerApplicationDetailInfoDTO.builder()
                .applicationStartAt(lockerApplication.getApplicationStartAt())
                .applicationEndAt(lockerApplication.getApplicationEndAt())
                .semester(lockerApplication.getApplicationSemester())
                .applicationType(lockerApplication.getApplicationType().name())
                .applicantTotalCount(memberApplications.size())
                .canAssign(lockerApplication.isCanAssign())
                .applicants(applicantDTOs)
                .build();
    }

    // 사물함 배정 정보
    public static LockerResponse.LockerAssignmentInfoDTO toLockerAssignmentInfoDTO(LockerAssignment lockerAssignment) {
        Locker locker = lockerAssignment.getLocker();
        Member member = lockerAssignment.getMember();

        String lockerNumber = locker.getLockerSection() + locker.getLockerNumber();

        return LockerResponse.LockerAssignmentInfoDTO.builder()
                .lockerId(locker.getId())
                .lockerNumber(lockerNumber)
                .studentId(member.getStudentId())
                .studentName(member.getStudentName())
                .build();
    }

    // 사물함 배정 정보 리스트
    public static List<LockerResponse.LockerAssignmentInfoDTO> toLockerAssignmentInfoDTOList(List<LockerAssignment> lockerAssignments) {
        return lockerAssignments.stream()
                .map(LockerResponseConverter::toLockerAssignmentInfoDTO)
                .toList();
    }
}

