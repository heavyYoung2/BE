package hongik.heavyYoung.domain.event.service.admin;

import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import jakarta.validation.Valid;

public interface AdminEventCommandService {
    EventResponse.EventAddResponseDTO createEvent(EventRequest.EventAddRequestDTO eventAddRequestDTO);
}
