package hongik.heavyYoung.domain.event.service.admin.impl;

import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.EventException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminEventCommandServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private AdminEventCommandServiceImpl adminEventCommandService;

    @Test
    @DisplayName("공지사항 생성 성공")
    void createEvent_success() {
        // given
        EventRequest.EventAddRequestDTO eventAddRequestDTO = EventRequest.EventAddRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        Event fakeSavedEvent = Event.builder()
                .id(1L)
                .eventTitle("간식행사")
                .eventContent("간식행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        given(eventRepository.save(any(Event.class))).willReturn(fakeSavedEvent);

        // when
        EventResponse.EventAddResponseDTO eventAddResponseDTO = adminEventCommandService.createEvent(eventAddRequestDTO);

        // then
        assertThat(eventAddResponseDTO.getEventId()).isEqualTo(1L);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("공지사항 삭제 성공")
    void deleteEvent_success() {
        // given
        Long eventId = 1L;
        Event event = Event.builder()
                .id(eventId)
                .eventTitle("간식행사")
                .eventContent("간식행사 세부 일정")
                .build();

        given(eventRepository.findById(eventId)).willReturn(Optional.of(event));

        // when
        adminEventCommandService.deleteEvent(eventId);

        // then
        verify(eventRepository).findById(eventId);
        verify(eventRepository).delete(event);
    }

    @Test
    @DisplayName("공지사항 삭제 실패 - 존재하지 않는 공지사항")
    void deleteEvent_fail_eventNotFound() {
        // given
        Long notExistingId = 999L;
        given(eventRepository.findById(notExistingId)).willReturn(Optional.empty());

        // when & then
        EventException eventException = assertThrows(EventException.class, () ->
                adminEventCommandService.deleteEvent(notExistingId));

        assertEquals(ErrorStatus.EVENT_NOT_FOUND, eventException.getCode());
        verify(eventRepository).findById(notExistingId);
        verify(eventRepository, never()).delete(any(Event.class));
    }

    @Test
    @DisplayName("공지사항 수정 성공")
    void updateEvent_success() {
        // given
        Long eventId = 1L;

        Event event = Event.builder()
                .id(eventId)
                .eventTitle("간식행사")
                .eventContent("간식행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        EventRequest.EventPutRequestDTO eventPutRequestDTO = EventRequest.EventPutRequestDTO.builder()
                .title("수정된행사")
                .content("수정된행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 10, 1))
                .eventEndDate(LocalDate.of(2025, 10, 2))
                .build();

        given(eventRepository.findById(eventId)).willReturn(Optional.of(event));

        // when
        EventResponse.EventPutResponseDTO eventPutResponseDTO = adminEventCommandService.updateEvent(eventId, eventPutRequestDTO);

        // then
        assertThat(eventPutResponseDTO.getEventId()).isEqualTo(1L);
        assertEquals(event.getEventTitle(),"수정된행사");
        assertEquals(event.getEventContent(), "수정된행사 세부 일정");
        assertEquals(event.getEventStartDate(), LocalDate.of(2025,10,1));
        assertEquals(event.getEventEndDate(), LocalDate.of(2025,10,2));

        verify(eventRepository).findById(eventId);
    }

    @Test
    @DisplayName("공지사항 수정 실패 - 존재하지 않는 공지사항")
    void updateEvent_fail_notExistingEvent() {
        // given
        Long notExistingId = 999L;

        EventRequest.EventPutRequestDTO eventPutRequestDTO = EventRequest.EventPutRequestDTO.builder()
                .title("수정된행사")
                .content("수정된행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 10, 1))
                .eventEndDate(LocalDate.of(2025, 10, 2))
                .build();

        given(eventRepository.findById(notExistingId)).willReturn(Optional.empty());

        // when & then
        EventException eventException = assertThrows(EventException.class, () ->
                adminEventCommandService.updateEvent(notExistingId, eventPutRequestDTO));

        assertEquals(ErrorStatus.EVENT_NOT_FOUND, eventException.getCode());
        verify(eventRepository).findById(notExistingId);
        verify(eventRepository, never()).save(any(Event.class));
    }
}