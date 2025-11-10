package hongik.heavyYoung.domain.locker.controller.admin;

import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.locker.dto.LockerRequest;
import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.service.admin.AdminLockerCommandService;
import hongik.heavyYoung.domain.locker.service.admin.AdminLockerQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.LockerException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/applications")
    public ApiResponse<List<LockerResponse.LockerApplicationInfoDTO>> getAllLockerApplication() {
        List<LockerResponse.LockerApplicationInfoDTO> allLockerApplication = adminLockerQueryService.findAllLockerApplication();
        return ApiResponse.onSuccess(allLockerApplication);
    }

    @GetMapping("/applications/{lockerApplicationId}")
    public ApiResponse<LockerResponse.LockerApplicationDetailInfoDTO> getLockerApplicationDetail(
            @PathVariable("lockerApplicationId") Long lockerApplicationId
    ) {
        LockerResponse.LockerApplicationDetailInfoDTO lockerApplicationDetail = adminLockerQueryService.findLockerApplicationDetail(lockerApplicationId);
        return ApiResponse.onSuccess(lockerApplicationDetail);
    }

    @PostMapping("/applications")
    public ApiResponse<Void> addLockerApplication(
            @RequestBody @Valid LockerRequest.LockerApplicationAddRequestDTO lockerApplicationAddRequestDTO
    ) {
        // ApplicationType - Locker 신청 관련 Enum 검증 작업
        if (!ApplicationType.LOCKER.contains(lockerApplicationAddRequestDTO.getApplicationType())) {
            throw new LockerException(ErrorStatus.VALIDATION_ERROR);
        }

        adminLockerCommandService.addLockerApplication(lockerApplicationAddRequestDTO);
        return ApiResponse.onSuccess(null);
    }
}
