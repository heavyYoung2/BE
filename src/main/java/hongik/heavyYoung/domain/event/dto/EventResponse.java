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
        Long eventId;
        String title;
        LocalDateTime eventCreatedAt;
        LocalDate eventStartAt;
        LocalDate eventEndAt;
    }
}
