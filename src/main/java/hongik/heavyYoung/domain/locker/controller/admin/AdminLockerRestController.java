package hongik.heavyYoung.domain.locker.controller.admin;

import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.locker.dto.LockerRequest;
import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.service.admin.AdminLockerCommandService;
import hongik.heavyYoung.domain.locker.service.admin.AdminLockerQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.LockerException;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/lockers")
@Tag(name = "Locker API - 학생회", description = "학생회 - 사물함 관련 API")
@Validated
@RequiredArgsConstructor
public class AdminLockerRestController {

    private final AdminLockerQueryService adminLockerQueryService;
    private final AdminLockerCommandService adminLockerCommandService;

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "사물함 신청 전체 조회")
    @GetMapping("/applications")
    public ApiResponse<List<LockerResponse.LockerApplicationInfoDTO>> getAllLockerApplication(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        List<LockerResponse.LockerApplicationInfoDTO> allLockerApplication = adminLockerQueryService.findAllLockerApplication();
        return ApiResponse.onSuccess(allLockerApplication);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "사물함 신청 내역 상세 조회")
    @GetMapping("/applications/{lockerApplicationId}")
    public ApiResponse<LockerResponse.LockerApplicationDetailInfoDTO> getLockerApplicationDetail(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @PathVariable("lockerApplicationId") Long lockerApplicationId
    ) {
        LockerResponse.LockerApplicationDetailInfoDTO lockerApplicationDetail = adminLockerQueryService.findLockerApplicationDetail(lockerApplicationId);
        return ApiResponse.onSuccess(lockerApplicationDetail);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "사물함 신청 마감")
    @PatchMapping("/applications/{lockerApplicationId}")
    public ApiResponse<Void> endLockerApplication(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @PathVariable("lockerApplicationId") Long lockerApplicationId
    ) {
        adminLockerCommandService.finishLockerApplication(lockerApplicationId);
        return ApiResponse.onSuccess(null);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "사물함 신청 추가")
    @PostMapping("/applications")
    public ApiResponse<Void> addLockerApplication(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @RequestBody @Valid LockerRequest.LockerApplicationAddRequestDTO lockerApplicationAddRequestDTO
    ) {
        // ApplicationType - Locker 신청 관련 Enum 검증 작업
        if (!ApplicationType.LOCKER.contains(lockerApplicationAddRequestDTO.getApplicationType())) {
            throw new LockerException(ErrorStatus.VALIDATION_ERROR);
        }

        // 사물함 신청 시작일, 마감일 검증 작업
        if (lockerApplicationAddRequestDTO.getApplicationStartAt().isAfter(lockerApplicationAddRequestDTO.getApplicationEndAt())) {
            throw new LockerException(ErrorStatus.LOCKER_APPLICATION_INVALID_DATE_FORMAT);
        }

        adminLockerCommandService.addLockerApplication(lockerApplicationAddRequestDTO);
        return ApiResponse.onSuccess(null);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "사물함 신청 내역으로 사물함 배정")
    @PostMapping("/applications/assign/{lockerApplicationId}")
    public ApiResponse<Void> assignLockers(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @PathVariable ("lockerApplicationId") @Positive(message = "lockerApplicationId는 1 이상이어야 합니다.") Long lockerApplicationId
    ) {
        adminLockerCommandService.assignLockersByApplication(lockerApplicationId);
        return ApiResponse.onSuccess(null);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "해당 학기 사물함 배정 내역 조회, default 값은 가장 최신 학기")
    @GetMapping("/applications/assign")
    public ApiResponse<List<LockerResponse.LockerAssignmentInfoDTO>> getLockerAssignments(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @Parameter(description = "학기", example = "2025-1")
            @Pattern(regexp = "^[0-9]{4}-[1-2]$", message = "학기 형식은 'YYYY-1' 또는 'YYYY-2' 여야 합니다.")
            @RequestParam(value = "semester", required = false) String semester
    ) {
        List<LockerResponse.LockerAssignmentInfoDTO> lockerAssignmentInfoDTOs = adminLockerQueryService.findLockerAssignments(semester);
        return ApiResponse.onSuccess(lockerAssignmentInfoDTOs);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "학기 정보 조회")
    @GetMapping("/applications/assign/semester")
    public ApiResponse<List<String>> getLockerAssignSemesters(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        List<String> lockerAssignSemesters = adminLockerQueryService.findLockerAssignSemesters();
        return ApiResponse.onSuccess(lockerAssignSemesters);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "사물함 일괄 반납")
    @PatchMapping("/return")
    public ApiResponse<Void> returnLockers(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        adminLockerCommandService.returnCurrentSemesterLockers();
        return ApiResponse.onSuccess(null);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "사물함 상태 사용불가로 변경")
    @PatchMapping("/{lockerId}/unavailable")
    public ApiResponse<Void> makeLockerNotAvailable(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @PathVariable("lockerId") Long lockerId
    ) {
        adminLockerCommandService.changeLockerNotAvailable(lockerId);
        return ApiResponse.onSuccess(null);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "사물함 상태 사용가능으로 변경")
    @PatchMapping("/{lockerId}/available")
    public ApiResponse<Void> makeLockerAvailable(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @PathVariable("lockerId") Long lockerId
    ) {
        adminLockerCommandService.changeLockerAvailable(lockerId);
        return ApiResponse.onSuccess(null);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "사물함 상태 사용중으로 변경(수동배정)")
    @PatchMapping("/{lockerId}/using")
    public ApiResponse<Void> makeLockerUsing(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @PathVariable("lockerId") Long lockerId,
            @Parameter(description = "학번", example = "C011117")
            @RequestParam(value = "studentId", required = false) String studentId
    ) {
        adminLockerCommandService.changeLockerUsing(lockerId, studentId);
        return ApiResponse.onSuccess(null);
    }
}
