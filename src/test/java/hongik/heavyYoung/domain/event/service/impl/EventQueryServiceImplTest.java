package hongik.heavyYoung.domain.event.service.impl;

import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EventQueryServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventQueryServiceImpl eventQueryService;

    @Test
    @DisplayName("공지사항 조회(전체) 성공")
    void getAllEvents_success() {
        // given
        List<Event> events = new ArrayList<>();

        Event event1 = Event.builder()
                .id(1L)
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartAt(LocalDate.of(2025, 9, 1))
                .eventEndAt(LocalDate.of(2025, 9, 2))
                .build();

        Event event2 = Event.builder()
                .id(2L)
                .eventTitle("나눔행사")
                .eventContent("나눔행사 상세 일정")
                .eventStartAt(LocalDate.of(2025, 9, 1))
                .eventEndAt(LocalDate.of(2025, 9, 2))
                .build();

        events.add(event1);
        events.add(event2);

        given(eventRepository.findAll()).willReturn(events);

        // when
        List<EventResponse.EventInfoDTO> result = eventQueryService.getAllEvents(null, null);

        // then
        assertThat(result).hasSize(2);
        assertEquals(result.getFirst().getEventId(), 1L);
        assertEquals(result.get(1).getEventId(),2L);
    }

    @Test
    @DisplayName("공지사항 조회(기간별) 성공")
    void getAllEventsWithPeriod_success() {
        // given
        LocalDate from = LocalDate.of(2025, 9, 1);
        LocalDate to = LocalDate.of(2025, 9, 2);

        Event event1 = Event.builder()
                .id(1L)
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartAt(LocalDate.of(2025, 9, 1))
                .eventEndAt(LocalDate.of(2025, 9, 2))
                .build();

        given(eventRepository.findAllByEventStartAtBetween(from,to)).willReturn(List.of(event1));

        // when
        List<EventResponse.EventInfoDTO> result = eventQueryService.getAllEvents(from, to);

        // then
        assertThat(result).hasSize(1);
        assertEquals(result.getFirst().getEventId(), 1L);
    }
}