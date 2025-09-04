package hongik.heavyYoung.domain.event.converter;

import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventRequestConverter {

    public static Event toNewEntity(EventRequest.EventAddRequestDTO eventAddRequestDTO) {
        return Event.builder()
                .eventTitle(eventAddRequestDTO.getTitle())
                .eventContent(eventAddRequestDTO.getContent())
                .eventStartDate(eventAddRequestDTO.getEventStartDate())
                .eventEndDate(eventAddRequestDTO.getEventEndDate())
                // TODO 사진 업로드 방식 결정 후, imageUrl 혹은 Multipart 추가
                .build();
    }
}
