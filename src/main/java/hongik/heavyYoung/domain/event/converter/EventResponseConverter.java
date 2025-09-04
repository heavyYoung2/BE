package hongik.heavyYoung.domain.event.converter;

import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.entity.EventImage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventResponseConverter {

    public static EventResponse.EventInfoDTO toEventInfoDTO(Event event) {
        return EventResponse.EventInfoDTO.builder()
                .eventId(event.getId())
                .title(event.getEventTitle())
                .eventCreatedAt(event.getCreatedAt())
                .eventUpdatedAt(event.getUpdatedAt())
                .eventStartDate(event.getEventStartDate())
                .eventEndDate(event.getEventEndDate())
                .build();
    }

    public static List<EventResponse.EventInfoDTO> toEventInfoDTOList(List<Event> events) {
        return events.stream()
                .map(EventResponseConverter::toEventInfoDTO)
                .toList();
    }

    public static EventResponse.EventInfoDetailDTO toEventInfoDetailDTO(Event event) {
        return EventResponse.EventInfoDetailDTO.builder()
                .eventId(event.getId())
                .title(event.getEventTitle())
                .content(event.getEventContent())
                .eventStartDate(event.getEventStartDate())
                .eventEndDate(event.getEventEndDate())
                .eventCreatedAt(event.getCreatedAt())
                .eventUpdatedAt(event.getUpdatedAt())
                .imageUrls(event.getEventImages().stream()
                        .map(EventImage::getEventImageUrl)
                        .toList())
                .build();
    }

    public static EventResponse.EventAddResponseDTO toEventAddResponseDTO(Event event) {
        return EventResponse.EventAddResponseDTO.builder()
                .eventId(event.getId())
                .build();
    }
}
