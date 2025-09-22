package hongik.heavyYoung.domain.event.command;

import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.EventException;

import java.time.LocalDate;

public record CreateEventCommand (
        String title,
        String content,
        LocalDate startDate,
        LocalDate endDate
        // TODO 사진 업로드 방식 결정 후, imageUrl 혹은 Multipart 추가
) {
    public static CreateEventCommand from(EventRequest.EventAddRequestDTO eventAddRequestDTO) {
        CreateEventCommand createEventCommand = new CreateEventCommand(
                eventAddRequestDTO.getTitle(),
                eventAddRequestDTO.getContent(),
                eventAddRequestDTO.getEventStartDate(),
                eventAddRequestDTO.getEventEndDate()
                // TODO 사진 업로드 방식 결정 후, imageUrl 혹은 Multipart 추가
        );
        createEventCommand.validate();
        return createEventCommand;
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
