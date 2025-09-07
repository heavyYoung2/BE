package hongik.heavyYoung.domain.event.service.admin.impl;

import hongik.heavyYoung.domain.event.dto.EventRequest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminEventCommandServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private AdminEventCommandServiceImpl adminEventCommandService;

    @Test
    @DisplayName("공지사항 생성 성공")
    void createEvent(){
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


}