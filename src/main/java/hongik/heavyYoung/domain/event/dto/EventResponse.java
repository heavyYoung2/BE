package hongik.heavyYoung.domain.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EventResponse {
    // 공지사항 기본 정보 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventInfoDTO {
        @Schema(description = "공지사항 PK")
        private Long eventId;
        @Schema(description = "공지사항 제목")
        private String title;
        @Schema(description = "공지사항 생성 시간", example = "2025-08-31 00:00:00")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventCreatedAt;
        @Schema(description = "행사 시작일")
        private LocalDate eventStartDate;
        @Schema(description = "행사 종료일")
        private LocalDate eventEndDate;
    }

    // 공지사항 상세 정보 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventInfoDetailDTO {
        @Schema(description = "공지사항 PK")
        private Long eventId;
        @Schema(description = "공지사항 제목")
        private String title;
        @Schema(description = "공지사항 내용")
        private String content;
        @Schema(description = "행사 시작일")
        private LocalDate eventStartDate;
        @Schema(description = "행사 종료일")
        private LocalDate eventEndDate;
        @Schema(description = "공지사항 생성 시간", example = "2025-08-31 00:00:00")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventCreatedAt;
        @Schema(description = "공지사항 사진")
        private List<String> imageUrls;
    }


    // 공지사항 생성시 응답 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventAddResponseDTO {
        @Schema(description = "공지사항 PK")
        private Long eventId;
    }
}
