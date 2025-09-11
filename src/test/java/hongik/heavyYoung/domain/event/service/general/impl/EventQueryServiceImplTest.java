package hongik.heavyYoung.domain.event.service.general.impl;

import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.entity.EventImage;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import hongik.heavyYoung.domain.event.service.general.impl.EventQueryServiceImpl;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.EventException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventQueryServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventQueryServiceImpl eventQueryService;

    @Test
    @DisplayName("공지사항 조회(전체) 성공")
    void findEvents_success() {
        // given
        List<Event> events = new ArrayList<>();

        Event event1 = Event.builder()
                .id(1L)
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        Event event2 = Event.builder()
                .id(2L)
                .eventTitle("나눔행사")
                .eventContent("나눔행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 10, 1))
                .eventEndDate(LocalDate.of(2025, 10, 2))
                .build();

        events.add(event2);
        events.add(event1);

        given(eventRepository.findAllByOrderByCreatedAtDesc()).willReturn(events);

        // when
        List<EventResponse.EventInfoDTO> result = eventQueryService.findEvents(null, null);

        // then
        assertThat(result).hasSize(2);
        assertEquals(result.getFirst().getEventId(), 2L);
        assertEquals(result.get(1).getEventId(),1L);

        verify(eventRepository).findAllByOrderByCreatedAtDesc();
    }

    @Test
    @DisplayName("공지사항 조회(기간별) 성공")
    void findEventsWithPeriod_success() {
        // given
        List<Event> events = new ArrayList<>();

        LocalDate from = LocalDate.of(2025, 9, 1);
        LocalDate to = LocalDate.of(2025, 9, 30);


        Event event1 = Event.builder()
                .id(1L)
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        Event event2 = Event.builder()
                .id(2L)
                .eventTitle("나눔행사")
                .eventContent("나눔행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 4))
                .eventEndDate(LocalDate.of(2025, 9, 5))
                .build();

        events.add(event2);
        events.add(event1);

        given(eventRepository.findAllByEventStartDateBetweenOrderByCreatedAtDesc(from,to)).willReturn(events);

        // when
        List<EventResponse.EventInfoDTO> result = eventQueryService.findEvents(from, to);

        // then
        assertThat(result).hasSize(2);
        assertEquals(result.getFirst().getEventId(), 2L);

        verify(eventRepository).findAllByEventStartDateBetweenOrderByCreatedAtDesc(from, to);
    }

    @Test
    @DisplayName("공지사항 상세 조회(사진포함) 성공")
    void findEventDetails() {
        // given
        Event event1 = Event.builder()
                .id(1L)
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        EventImage eventImage1 = EventImage.builder()
                .event(event1)
                .eventImageUrl("url1")
                .sortOrder(1)
                .build();

        EventImage eventImage2 = EventImage.builder()
                .event(event1)
                .eventImageUrl("url2")
                .sortOrder(2)
                .build();

        event1.getEventImages().add(eventImage1);
        event1.getEventImages().add(eventImage2);

        given(eventRepository.findByIdWithImages(event1.getId()))
                .willReturn(Optional.of(event1));

        // when
        EventResponse.EventInfoDetailDTO result = eventQueryService.findEventDetails(event1.getId());

        // then
        assertThat(result.getImageUrls()).hasSize(2);
        assertEquals(result.getContent(), "간식행사 상세 일정");
        assertThat(result.getImageUrls()).containsExactly("url1", "url2");

        verify(eventRepository).findByIdWithImages(event1.getId());
    }

    @Test
    @DisplayName("공지사항 상세 조회(사진포함) - 존재하지 않는 공지사항의 경우 예외 발생")
    void findEventDetails_eventNotFound_throwsException() {
        // given
        Long notExistingId = 999L;
        given(eventRepository.findByIdWithImages(notExistingId))
                .willReturn(Optional.empty());

        // when & then
        EventException exception = assertThrows(EventException.class, () ->
                eventQueryService.findEventDetails(notExistingId));

        assertEquals(ErrorStatus.EVENT_NOT_FOUND, exception.getCode());
        verify(eventRepository).findByIdWithImages(notExistingId);
    }

}