package hongik.heavyYoung.domain.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class EventRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventAddRequestDTO {
        @Schema(description = "공지사항 제목")
        private String title;
        @Schema(description = "공지사항 내용")
        private String content;
        @Schema(description = "행사 시작일")
        private LocalDate eventStartDate;
        @Schema(description = "행사 종료일")
        private LocalDate eventEndDate;
        // TODO 사진 업로드 방식 결정 후, imageUrl 혹은 Multipart 추가
    }
}
