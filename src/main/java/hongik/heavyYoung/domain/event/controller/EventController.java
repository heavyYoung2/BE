package hongik.heavyYoung.domain.event.controller;

import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.service.EventQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.GeneralException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventQueryService eventService;

    @Operation(summary = "공지사항 조회")
    @GetMapping("")
    public ApiResponse<List<EventResponse.EventInfoDTO>> getEvents(
            @Parameter(description = "시작일 (yyyy-MM-dd)")
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @Parameter(description = "시작일 (yyyy-MM-dd)")
            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to
    ) {
        // 시작일(from), 종료일(to) 둘 중 한 가지 값만 들어온 경우 예외 처리
        if ((from == null && to != null) || (from != null && to == null)) {
            throw new GeneralException(ErrorStatus.INVALID_PARAMETER);
        }

        // 시작일(from)보다 종료일(to)이 앞선 경우 예외 처리
        if (from != null && to != null && from.isAfter(to)) {
            throw new GeneralException(ErrorStatus.INVALID_DATE_RANGE);
        }

        List<EventResponse.EventInfoDTO> allEvents = eventService.getAllEvents(from, to);
        return ApiResponse.onSuccess(allEvents);
    };
}
