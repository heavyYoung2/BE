package hongik.heavyYoung.domain.event.converter;

import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.entity.EventImage;

import java.util.Comparator;
import java.util.List;

public class EventResponseConverter {
    // 공지사항 기본 정보
    public static EventResponse.EventInfoDTO toEventInfoDTO(Event event) {
        return EventResponse.EventInfoDTO.builder()
                .eventId(event.getId())
                .title(event.getEventTitle())
                .eventCreatedAt(event.getCreatedAt())
                .eventStartDate(event.getEventStartDate())
                .eventEndDate(event.getEventEndDate())
                .build();
    }

    // 공지사항 기본 정보를 리스트로 변환
    public static List<EventResponse.EventInfoDTO> toEventInfoDTOList(List<Event> events) {
        return events.stream()
                .map(EventResponseConverter::toEventInfoDTO)
                .toList();
    }

    // 공지사항 상세 정보
    public static EventResponse.EventInfoDetailDTO toEventInfoDetailDTO(Event event) {
        List<String> imageUrls = event.getEventImages().stream()
                .sorted(Comparator.comparing(EventImage::getSortOrder))
                .map(EventImage::getEventImageUrl)
                .toList();

        return EventResponse.EventInfoDetailDTO.builder()
                .eventId(event.getId())
                .title(event.getEventTitle())
                .content(event.getEventContent())
                .eventStartDate(event.getEventStartDate())
                .eventEndDate(event.getEventEndDate())
                .eventCreatedAt(event.getCreatedAt())
                .imageUrls(imageUrls)
                .build();
    }

    public static EventResponse.EventAddResponseDTO toEventAddResponseDTO(Event event) {
        return EventResponse.EventAddResponseDTO.builder()
                .eventId(event.getId())
                .build();
    }

    public static EventResponse.EventPutResponseDTO toEventPutResponseDTO(Event event) {
        return EventResponse.EventPutResponseDTO.builder()
                .eventId(event.getId())
                .build();
    }
}
