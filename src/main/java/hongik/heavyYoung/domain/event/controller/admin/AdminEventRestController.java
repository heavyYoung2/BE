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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/event")
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
}
