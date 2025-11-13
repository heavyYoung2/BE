package hongik.heavyYoung.domain.locker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class LockerResponse {
    // 사물함 기본 정보 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LockerInfoDTO {
        @Schema(description = "사물함 PK")
        private Long lockerId;
        @Schema(description = "사물함 번호")
        private String lockerNumber;
        @Schema(description = "사물함 상태")
        private String lockerStatus;
        @Schema(description = "사물함 소유 학생 학번")
        private String studentId;
        @Schema(description = "사물함 소유 학생 이름")
        private String studentName;
    }

    // 나의 사물함 정보 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyLockerInfoDTO {
        @Schema(description = "사물함 PK")
        private Long lockerId;
        @Schema(description = "사물함 번호")
        private String lockerNumber;
        @Schema(description = "사물함 대여 상태")
        private String lockerRentalStatus;
    }

    // 사물함 신청 정보 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LockerApplicationInfoDTO {
        private Long applicationId;
        private LocalDateTime applicationStartAt;
        private LocalDateTime applicationEndAt;
        private String semester;
        private String applicationType;
        private boolean canApply;
        private boolean canAssign;
    }

    // 사물함 신청 상세 정보 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LockerApplicationDetailInfoDTO {

        private LocalDateTime applicationStartAt;
        private LocalDateTime applicationEndAt;
        private String semester;
        private String applicationType;
        private int applicantTotalCount;
        private boolean canAssign;
        private List<ApplicantInfoDTO> applicants;

        @Getter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ApplicantInfoDTO {
            private String studentId;
            private String studentName;
            private LocalDateTime appliedAt;
        }
    }

    // 사물함 배정 내역 정보 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LockerAssignmentInfoDTO {
        private Long lockerId;
        private String lockerNumber;
        private String studentId;
        private String studentName;
    }

    // 사물함 신청 학기 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SemesterDTO {
        private String semester;
    }
}
