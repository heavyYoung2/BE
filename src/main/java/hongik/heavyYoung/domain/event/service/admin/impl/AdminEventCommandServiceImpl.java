package hongik.heavyYoung.domain.event.service.admin.impl;

import hongik.heavyYoung.domain.event.converter.EventRequestConverter;
import hongik.heavyYoung.domain.event.converter.EventResponseConverter;
import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import hongik.heavyYoung.domain.event.service.admin.AdminEventCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminEventCommandServiceImpl implements AdminEventCommandService {

    private final EventRepository eventRepository;

    @Override
    @Transactional
    public EventResponse.EventAddResponseDTO createEvent(EventRequest.EventAddRequestDTO eventAddRequestDTO) {
        Event event = EventRequestConverter.toNewEvent(eventAddRequestDTO);
        Event savedEvent = eventRepository.save(event);
        return EventResponseConverter.toEventAddResponseDTO(savedEvent);
    }
}
