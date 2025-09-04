package hongik.heavyYoung.domain.event.service;

import hongik.heavyYoung.domain.event.dto.EventResponse;

import java.time.LocalDate;
import java.util.List;

public interface EventQueryService {
    List<EventResponse.EventInfoDTO> getAllEvents(LocalDate from, LocalDate to);
}
