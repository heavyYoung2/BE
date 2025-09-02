package hongik.heavyYoung.domain.event.converter;

import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.entity.Event;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventResponseConverter {

    public static EventResponse.EventInfoDTO toEventInfoDTO(Event event) {
        return EventResponse.EventInfoDTO.builder()
                .eventId(event.getId())
                .title(event.getEventTitle())
                .eventCreatedAt(event.getCreatedAt())
                .eventStartAt(event.getEventStartAt())
                .eventEndAt(event.getEventEndAt())
                .build();
    }

    public static List<EventResponse.EventInfoDTO> toEventInfoDTOList(List<Event> events) {
        return events.stream()
                .map(EventResponseConverter::toEventInfoDTO)
                .toList();
    }
}
