package hongik.heavyYoung.domain.event.service.general;

import hongik.heavyYoung.domain.event.dto.EventResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface EventQueryService {
    List<EventResponseDTO.EventInfoDTO> findEvents(LocalDate from, LocalDate to);
    EventResponseDTO.EventInfoDetailDTO findEventDetails(Long id);
}
