package hongik.heavyYoung.domain.event.command;

import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.EventException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateEventCommandTest {

    @Test
    void createEventCommand_success() {
        // given
        EventRequest.EventAddRequestDTO eventAddRequestDTO = EventRequest.EventAddRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        // when & then
        assertThatCode(() -> CreateEventCommand.from(eventAddRequestDTO))
                .doesNotThrowAnyException();
    }

    @Test
    void createEventCommand_eventInvalidDateCombination() {
        // given
        EventRequest.EventAddRequestDTO eventAddRequestDTO = EventRequest.EventAddRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(null)
                .build();

        // when & then
        assertThatThrownBy(() -> CreateEventCommand.from(eventAddRequestDTO))
                .isInstanceOf(EventException.class)
                .extracting("code")
                .isEqualTo(ErrorStatus.EVENT_INVALID_DATE_COMBINATION);
    }

    @Test
    void createEventCommand_eventInvalidDateRange() {
        // given
        EventRequest.EventAddRequestDTO eventAddRequestDTO = EventRequest.EventAddRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부일정")
                .eventStartDate(LocalDate.of(2025, 9, 10))
                .eventEndDate(LocalDate.of(2025, 9, 1))
                .build();

        // when & then
        assertThatThrownBy(() -> CreateEventCommand.from(eventAddRequestDTO))
                .isInstanceOf(EventException.class)
                .extracting("code")
                .isEqualTo(ErrorStatus.EVENT_INVALID_DATE_RANGE);
    }
}
