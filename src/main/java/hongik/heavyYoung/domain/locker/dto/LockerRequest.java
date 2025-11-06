package hongik.heavyYoung.domain.locker.dto;

import hongik.heavyYoung.domain.application.enums.ApplicationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class LockerRequest {

    // 사물함 신청 생성 요청 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LockerApplicationAddRequestDTO {
        @Schema(description = "사물함 신청 시작일")
        @NotNull(message = "신청 시작일은 필수입니다.")
        private LocalDateTime applicationStartAt;
        @Schema(description = "사물함 신청 마감일")
        @NotNull(message = "신청 마감일은 필수입니다.")
        private LocalDateTime applicationEndAt;
        @Schema(description = "사물함 신청 학기 (예: 2025-2)")
        @Pattern(regexp = "^[0-9]{4}-[1-2]$", message = "학기 형식은 'YYYY-N' (예: 2025-2) 형태여야 합니다."
        )
        private String semester;
        @Schema(description = "사물함 신청 종류(본 신청, 추가 신청)")
        private ApplicationType applicationType;
    }
}
