package hongik.heavyYoung.domain.event.service.admin;

import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminEventCommandService {
    EventResponse.EventAddResponseDTO createEvent(EventRequest.EventAddRequestDTO eventAddRequestDTO, List<MultipartFile> multipartFiles);
    void deleteEvent(Long eventId);
    EventResponse.EventPutResponseDTO updateEvent(Long eventId, EventRequest.EventPutRequestDTO eventPutRequestDTO, List<MultipartFile> multipartFiles);
}
