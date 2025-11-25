package hongik.heavyYoung.domain.event.service.admin;

import hongik.heavyYoung.domain.event.dto.EventRequestDTO;
import hongik.heavyYoung.domain.event.dto.EventResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminEventCommandService {
    EventResponseDTO.EventAddResponseDTO createEvent(EventRequestDTO.EventAddRequestDTO eventAddRequestDTO, List<MultipartFile> multipartFiles);
    void deleteEvent(Long eventId);
    EventResponseDTO.EventPutResponseDTO updateEvent(Long eventId, EventRequestDTO.EventPutRequestDTO eventPutRequestDTO, List<MultipartFile> multipartFiles);
}
