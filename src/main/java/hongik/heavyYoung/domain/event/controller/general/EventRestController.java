package hongik.heavyYoung.domain.event.controller.general;

import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.service.general.EventQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.GeneralException;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/events")
@Validated
@Tag(name = "Event API - 학생", description = "학생 - 공지사항 관련 API")
@RequiredArgsConstructor
public class EventRestController {

    private final EventQueryService eventQueryService;

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "공지사항 조회")
    @GetMapping("")
    public ApiResponse<List<EventResponse.EventInfoDTO>> getEvents (
            @Parameter(description = "시작일 (yyyy-MM-dd)", example = "2025-09-01")
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @Parameter(description = "종료일 (yyyy-MM-dd)", example = "2025-09-30")
            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to,

            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        // 시작일(from), 종료일(to) 둘 중 한 가지 값만 들어온 경우 예외 처리
        if ((from == null && to != null) || (from != null && to == null)) {
            throw new GeneralException(ErrorStatus.INVALID_PARAMETER);
        }

        // 시작일(from)보다 종료일(to)이 앞선 경우 예외 처리
        if (from != null && to != null && from.isAfter(to)) {
            throw new GeneralException(ErrorStatus.INVALID_DATE_RANGE);
        }

        List<EventResponse.EventInfoDTO> allEvents = eventQueryService.findEvents(from, to);
        return ApiResponse.onSuccess(allEvents);
    }

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "공지사항 상세 조회")
    @GetMapping("/{eventId}")
    public ApiResponse<EventResponse.EventInfoDetailDTO> getEventDetails (
            @PathVariable("eventId")
            @Positive(message = "eventId는 1 이상이어야 합니다.") Long eventId,
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        EventResponse.EventInfoDetailDTO eventDetails = eventQueryService.findEventDetails(eventId);
        return ApiResponse.onSuccess(eventDetails);
    }
}
