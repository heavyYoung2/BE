package hongik.heavyYoung.domain.event.controller.admin;

import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.service.admin.AdminEventCommandService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
@Validated
@Tag(name = "Event API - 학생회", description = "학생회 - 공지사항 관련 API")
@RequiredArgsConstructor
public class AdminEventRestController {

    private final AdminEventCommandService adminEventCommandService;

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "공지사항 생성")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<EventResponse.EventAddResponseDTO> addEvent (
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @RequestPart("eventAddRequestDTO") @Valid EventRequest.EventAddRequestDTO eventAddRequestDTO,
            @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles
            ) {
        EventResponse.EventAddResponseDTO eventAddResponseDTO = adminEventCommandService.createEvent(eventAddRequestDTO, multipartFiles);
        return ApiResponse.onSuccess(eventAddResponseDTO);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "공지사항 삭제")
    @DeleteMapping("/{eventId}")
    public ApiResponse<Void> deleteEvent (
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @PathVariable("eventId") @Positive(message = "eventId는 1 이상이어야 합니다.") Long eventId
    ) {
        adminEventCommandService.deleteEvent(eventId);
        return ApiResponse.onSuccess(null);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "공지사항 수정")
    @PutMapping(value = "/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<EventResponse.EventPutResponseDTO> updateEvent (
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @PathVariable("eventId") @Positive(message = "eventId는 1 이상이어야 합니다.") Long eventId,
            @RequestPart("eventPutRequestDTO") @Valid EventRequest.EventPutRequestDTO eventPutRequestDTO,
            @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles
    ) {
        EventResponse.EventPutResponseDTO eventPutResponseDTO = adminEventCommandService.updateEvent(eventId, eventPutRequestDTO, multipartFiles);
        return ApiResponse.onSuccess(eventPutResponseDTO);
    }
}
