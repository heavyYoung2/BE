package hongik.heavyYoung.domain.event.dto;

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
        private Long eventId;
        private String title;
        private LocalDateTime eventCreatedAt;
        private LocalDateTime eventUpdatedAt;
        private LocalDate eventStartDate;
        private LocalDate eventEndDate;
    }
}
