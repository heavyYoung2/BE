package hongik.heavyYoung.domain.event.controller.admin;

import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.service.admin.AdminEventCommandService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/events")
@Tag(name = "Event API - 학생회", description = "학생회 - 공지사항 관련 API")
@RequiredArgsConstructor
public class AdminEventRestController {

    private final AdminEventCommandService adminEventCommandService;

    @PostMapping
    public ApiResponse<EventResponse.EventAddResponseDTO> addEvent(
            @RequestBody @Valid EventRequest.EventAddRequestDTO eventAddRequestDTO
    ) {
        EventResponse.EventAddResponseDTO eventAddResponseDTO = adminEventCommandService.createEvent(eventAddRequestDTO);
        return ApiResponse.onSuccess(eventAddResponseDTO);
    }


}
