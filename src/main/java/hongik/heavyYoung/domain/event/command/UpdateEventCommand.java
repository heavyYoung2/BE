package hongik.heavyYoung.domain.event.command;

import hongik.heavyYoung.domain.event.dto.EventRequestDTO;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.EventException;

import java.time.LocalDate;

public record UpdateEventCommand (
        String title,
        String content,
        LocalDate startDate,
        LocalDate endDate
) {
    public static UpdateEventCommand from(EventRequestDTO.EventPutRequestDTO eventPutRequestDTO) {
        UpdateEventCommand updateEventCommand = new UpdateEventCommand(
                eventPutRequestDTO.getTitle(),
                eventPutRequestDTO.getContent(),
                eventPutRequestDTO.getEventStartDate(),
                eventPutRequestDTO.getEventEndDate()
        );
        updateEventCommand.validate();
        return updateEventCommand;
    }

    public void validate() {
        // 시작일(startDate)이나 종료일(endDate) 둘 중 한 가지 값만 들어온 경우 예외 처리
        if ((startDate == null && endDate != null) || (startDate != null && endDate == null)) {
            throw new EventException(ErrorStatus.EVENT_INVALID_DATE_COMBINATION);
        }

        // 시작일(startDate)보다 종료일(endDate)이 앞선 경우 예외 처리
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new EventException(ErrorStatus.EVENT_INVALID_DATE_RANGE);
        }
    }
}
