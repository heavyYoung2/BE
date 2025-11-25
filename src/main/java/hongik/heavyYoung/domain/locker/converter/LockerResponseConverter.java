package hongik.heavyYoung.domain.locker.converter;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.entity.MemberApplication;
import hongik.heavyYoung.domain.locker.dto.LockerResponseDTO;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.enums.LockerRentalStatus;
import hongik.heavyYoung.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public class LockerResponseConverter {

    // 사물함 기본 정보
    public static LockerResponseDTO.LockerInfoDTO toLockerInfoDTO(Locker locker, Member member, String lockerStatus) {
        return LockerResponseDTO.LockerInfoDTO.builder()
                .lockerId(locker.getId())
                .lockerNumber(locker.getLockerSection() + locker.getLockerNumber())
                .lockerStatus(lockerStatus)
                .studentId(member != null ? member.getStudentId() : null)
                .studentName(member != null ? member.getStudentName() : null)
                .build();
    }

    // 나의 사물함 정보 (사물함 배정 o)
    public static LockerResponseDTO.MyLockerInfoDTO toMyLockerInfoDTO(Locker locker, LockerRentalStatus lockerRentalStatus) {
        return LockerResponseDTO.MyLockerInfoDTO.builder()
                .lockerId(locker.getId())
                .lockerNumber(locker.getLockerSection() + locker.getLockerNumber())
                .lockerRentalStatus(lockerRentalStatus.name())
                .build();
    }

    // 나의 사물함 정보 (사물함 배정 x)
    public static LockerResponseDTO.MyLockerInfoDTO toMyLockerInfoDTO(LockerRentalStatus lockerRentalStatus) {
        return LockerResponseDTO.MyLockerInfoDTO.builder()
                .lockerRentalStatus(lockerRentalStatus.name())
                .build();
    }


    // 사물함 신청 정보
    public static LockerResponseDTO.LockerApplicationInfoDTO toLockerApplicationInfoDTO(Application lockerApplication) {
        boolean isInTimeRange = !LocalDateTime.now().isBefore(lockerApplication.getApplicationStartAt()) && !LocalDateTime.now().isAfter(lockerApplication.getApplicationEndAt());
        boolean hasCapacity = lockerApplication.getApplicationMemberCount() < lockerApplication.getApplicationCanCount();
        boolean canApply = isInTimeRange && hasCapacity && lockerApplication.isCanApply();

        return LockerResponseDTO.LockerApplicationInfoDTO.builder()
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
    public static List<LockerResponseDTO.LockerApplicationInfoDTO> toLockerApplicationInfoDTOList(List<Application> applications) {
        return applications.stream()
                .map(LockerResponseConverter::toLockerApplicationInfoDTO)
                .toList();
    }

    // 사물함 신청 상세 정보
    public static LockerResponseDTO.LockerApplicationDetailInfoDTO toLockerApplicationDetailInfoDTO(
            Application lockerApplication,
            List<MemberApplication> memberApplications
    ) {
        List<LockerResponseDTO.LockerApplicationDetailInfoDTO.ApplicantInfoDTO> applicantDTOs =
                memberApplications.stream()
                        .map(memberApplication -> {
                            Member member = memberApplication.getMember();
                            return LockerResponseDTO.LockerApplicationDetailInfoDTO.ApplicantInfoDTO.builder()
                                    .studentId(member.getStudentId())
                                    .studentName(member.getStudentName())
                                    .appliedAt(memberApplication.getCreatedAt())
                                    .build();
                        })
                        .toList();

        return LockerResponseDTO.LockerApplicationDetailInfoDTO.builder()
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
    public static LockerResponseDTO.LockerAssignmentInfoDTO toLockerAssignmentInfoDTO(LockerAssignment lockerAssignment) {
        Locker locker = lockerAssignment.getLocker();
        Member member = lockerAssignment.getMember();

        String lockerNumber = locker.getLockerSection() + locker.getLockerNumber();

        return LockerResponseDTO.LockerAssignmentInfoDTO.builder()
                .lockerId(locker.getId())
                .lockerNumber(lockerNumber)
                .studentId(member.getStudentId())
                .studentName(member.getStudentName())
                .build();
    }

    // 사물함 배정 정보 리스트
    public static List<LockerResponseDTO.LockerAssignmentInfoDTO> toLockerAssignmentInfoDTOList(List<LockerAssignment> lockerAssignments) {
        return lockerAssignments.stream()
                .map(LockerResponseConverter::toLockerAssignmentInfoDTO)
                .toList();
    }
}

