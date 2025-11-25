package hongik.heavyYoung.domain.event.command;

import hongik.heavyYoung.domain.event.dto.EventRequestDTO;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.EventException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UpdateEventCommandTest {

    @Test
    void updateEventCommand_success() {
        // given
        EventRequestDTO.EventPutRequestDTO dto = EventRequestDTO.EventPutRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        // when & then
        assertThatCode(() -> UpdateEventCommand.from(dto))
                .doesNotThrowAnyException();
    }

    @Test
    void updateEventCommand_eventInvalidDateCombination() {
        // given
        EventRequestDTO.EventPutRequestDTO eventPutRequestDTO = EventRequestDTO.EventPutRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(null)
                .build();

        // when & then
        assertThatThrownBy(() -> UpdateEventCommand.from(eventPutRequestDTO))
                .isInstanceOf(EventException.class)
                .extracting("code")
                .isEqualTo(ErrorStatus.EVENT_INVALID_DATE_COMBINATION);
    }

    @Test
    void updateEventCommand_eventInvalidDateRange() {
        // given
        EventRequestDTO.EventPutRequestDTO eventPutRequestDTO = EventRequestDTO.EventPutRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부일정")
                .eventStartDate(LocalDate.of(2025, 9, 10))
                .eventEndDate(LocalDate.of(2025, 9, 1))
                .build();

        // when & then
        assertThatThrownBy(() -> UpdateEventCommand.from(eventPutRequestDTO))
                .isInstanceOf(EventException.class)
                .extracting("code")
                .isEqualTo(ErrorStatus.EVENT_INVALID_DATE_RANGE);
    }

}