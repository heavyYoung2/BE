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
@RequestMapping("/admin/events")
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
        EventResponse.EventPutResponseDTO eventPutResponseDTO = adminEventCommandService.updateEvent(eventId, eventPutRequestDTO);
        return ApiResponse.onSuccess(eventPutResponseDTO);
    }
}
