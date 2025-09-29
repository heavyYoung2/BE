package hongik.heavyYoung.domain.locker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
