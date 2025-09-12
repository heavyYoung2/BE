package hongik.heavyYoung.domain.event.controller.admin;

import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.service.admin.AdminEventCommandService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.GeneralException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/event")
@Validated
@Tag(name = "Event API - 학생회", description = "학생회 - 공지사항 관련 API")
@RequiredArgsConstructor
public class AdminEventRestController {

    private final AdminEventCommandService adminEventCommandService;

    @Operation(summary = "공지사항 생성")
    @PostMapping
    public ApiResponse<EventResponse.EventAddResponseDTO> addEvent (
            @RequestBody @Valid EventRequest.EventAddRequestDTO eventAddRequestDTO
    ) {
        // 시작일(startDate)나 종료일(endDate) 둘 중 한 가지 값만 들어온 경우 예외 처리
        if ((eventAddRequestDTO.getEventStartDate() == null && eventAddRequestDTO.getEventEndDate() != null) ||
                (eventAddRequestDTO.getEventStartDate() != null && eventAddRequestDTO.getEventEndDate() == null)){
            throw new GeneralException(ErrorStatus.INVALID_PARAMETER);
        }

        // 시작일(startDate)보다 종료일(endDate)이 앞선 경우 예외 처리
        if (eventAddRequestDTO.getEventStartDate() != null && eventAddRequestDTO.getEventEndDate() != null &&
                eventAddRequestDTO.getEventStartDate().isAfter(eventAddRequestDTO.getEventEndDate())) {
            throw new GeneralException(ErrorStatus.INVALID_DATE_RANGE);
        }

        EventResponse.EventAddResponseDTO eventAddResponseDTO = adminEventCommandService.createEvent(eventAddRequestDTO);
        return ApiResponse.onSuccess(eventAddResponseDTO);
    }

    @Operation(summary = "공지사항 삭제")
    @DeleteMapping("/{eventId}")
    public ApiResponse<Void> deleteEvent (
            @PathVariable("eventId") @Positive(message = "eventId는 1 이상이어야 합니다.") Long eventId
    ) {
        adminEventCommandService.deleteEvent(eventId);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "공지사항 수정")
    @PutMapping("/{eventId}")
    public ApiResponse<EventResponse.EventPutResponseDTO> updateEvent (
            @PathVariable("eventId") @Positive(message = "eventId는 1 이상이어야 합니다.") Long eventId,
            @RequestBody @Valid EventRequest.EventPutRequestDTO eventPutRequestDTO
    ) {
        // 시작일(startDate)나 종료일(endDate) 둘 중 한 가지 값만 들어온 경우 예외 처리
        if ((eventPutRequestDTO.getEventStartDate() == null && eventPutRequestDTO.getEventEndDate() != null) ||
                (eventPutRequestDTO.getEventStartDate() != null && eventPutRequestDTO.getEventEndDate() == null)){
            throw new GeneralException(ErrorStatus.INVALID_PARAMETER);
        }

        // 시작일(startDate)보다 종료일(endDate)이 앞선 경우 예외 처리
        if (eventPutRequestDTO.getEventStartDate() != null && eventPutRequestDTO.getEventEndDate() != null &&
                eventPutRequestDTO.getEventStartDate().isAfter(eventPutRequestDTO.getEventEndDate())) {
            throw new GeneralException(ErrorStatus.INVALID_DATE_RANGE);
        }

        EventResponse.EventPutResponseDTO eventPutResponseDTO = adminEventCommandService.updateEvent(eventId, eventPutRequestDTO);
        return ApiResponse.onSuccess(eventPutResponseDTO);
    }
}
