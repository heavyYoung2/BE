package hongik.heavyYoung.domain.event.service.general;

import hongik.heavyYoung.domain.event.dto.EventResponse;

import java.time.LocalDate;
import java.util.List;

public interface EventQueryService {
    List<EventResponse.EventInfoDTO> findEvents(LocalDate from, LocalDate to);
    EventResponse.EventInfoDetailDTO findEventDetails(Long id);
}
