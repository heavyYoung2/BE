package hongik.heavyYoung.domain.event.converter;

import hongik.heavyYoung.domain.event.dto.EventResponseDTO;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.entity.EventImage;

import java.util.Comparator;
import java.util.List;

public class EventResponseConverter {
    // 공지사항 기본 정보
    public static EventResponseDTO.EventInfoDTO toEventInfoDTO(Event event) {
        return EventResponseDTO.EventInfoDTO.builder()
                .eventId(event.getId())
                .title(event.getEventTitle())
                .eventCreatedAt(event.getCreatedAt())
                .eventStartDate(event.getEventStartDate())
                .eventEndDate(event.getEventEndDate())
                .build();
    }

    // 공지사항 기본 정보를 리스트로 변환
    public static List<EventResponseDTO.EventInfoDTO> toEventInfoDTOList(List<Event> events) {
        return events.stream()
                .map(EventResponseConverter::toEventInfoDTO)
                .toList();
    }

    // 공지사항 상세 정보
    public static EventResponseDTO.EventInfoDetailDTO toEventInfoDetailDTO(Event event) {
        List<String> imageUrls = event.getEventImages().stream()
                .sorted(Comparator.comparing(EventImage::getSortOrder))
                .map(EventImage::getEventImageUrl)
                .toList();

        return EventResponseDTO.EventInfoDetailDTO.builder()
                .eventId(event.getId())
                .title(event.getEventTitle())
                .content(event.getEventContent())
                .eventStartDate(event.getEventStartDate())
                .eventEndDate(event.getEventEndDate())
                .eventCreatedAt(event.getCreatedAt())
                .imageUrls(imageUrls)
                .build();
    }

    public static EventResponseDTO.EventAddResponseDTO toEventAddResponseDTO(Event event) {
        return EventResponseDTO.EventAddResponseDTO.builder()
                .eventId(event.getId())
                .build();
    }

    public static EventResponseDTO.EventPutResponseDTO toEventPutResponseDTO(Event event) {
        return EventResponseDTO.EventPutResponseDTO.builder()
                .eventId(event.getId())
                .build();
    }
}
