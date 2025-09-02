package hongik.heavyYoung.domain.event.service.impl;

import hongik.heavyYoung.domain.event.converter.EventResponseConverter;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import hongik.heavyYoung.domain.event.service.EventQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventQueryServiceImpl implements EventQueryService {

    private final EventRepository eventRepository;

    @Override
    public List<EventResponse.EventInfoDTO> getAllEvents(LocalDate from, LocalDate to) {
        List<Event> allEvents;

        // 시작일, 종료일이 전달된 경우 (기간별 조회)
        if(from != null &&  to!=null){
            allEvents = eventRepository.findAllByEventStartAtBetween(from, to);
        }

        // 시작일, 종료일 둘 다 전달되지 않은 경우 (전체 조회)
        else{
            allEvents = eventRepository.findAll();
        }

        return EventResponseConverter.toEventInfoDTOList(allEvents);
    }
}
