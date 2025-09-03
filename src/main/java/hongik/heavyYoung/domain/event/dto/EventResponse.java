package hongik.heavyYoung.domain.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventInfoDTO{
        @Schema(description = "공지사항 PK")
        private Long eventId;
        @Schema(description = "공지사항 제목")
        private String title;
        @Schema(description = "공지사항 생성 시간", example = "2025-08-31 00:00:00")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventCreatedAt;
        @Schema(description = "공지사항 수정 시간", example = "2025-08-31 12:30:45")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm::ss")
        private LocalDateTime eventUpdatedAt;
        @Schema(description = "행사 시작일")
        private LocalDate eventStartDate;
        @Schema(description = "행사 종료일")
        private LocalDate eventEndDate;
    }
}
