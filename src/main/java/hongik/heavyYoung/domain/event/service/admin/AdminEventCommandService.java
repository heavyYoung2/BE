package hongik.heavyYoung.domain.event.service.admin;

import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.dto.EventResponse;

public interface AdminEventCommandService {
    EventResponse.EventAddResponseDTO createEvent(EventRequest.EventAddRequestDTO eventAddRequestDTO);
    void deleteEvent(Long eventId);
    EventResponse.EventPutResponseDTO updateEvent(Long eventId, EventRequest.EventPutRequestDTO eventPatchRequestDTO);
}
