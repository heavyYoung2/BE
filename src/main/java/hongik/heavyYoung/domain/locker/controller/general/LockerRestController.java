package hongik.heavyYoung.domain.locker.controller.general;

import hongik.heavyYoung.domain.locker.dto.LockerResponseDTO;
import hongik.heavyYoung.domain.locker.service.general.LockerQueryService;
import hongik.heavyYoung.domain.locker.service.general.MyLockerCommandService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lockers")
@Tag(name = "Locker API - 학생", description = "학생 - 사물함 관련 API")
@Validated
@RequiredArgsConstructor
public class LockerRestController {

    private final LockerQueryService lockerQueryService;
    private final MyLockerCommandService myLockerCommandService;

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "사물함 전체 조회")
    @GetMapping
    public ApiResponse<List<LockerResponseDTO.LockerInfoDTO>> getAllLockers(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @Parameter(description = "사물함 구역", example = "A", required = true)
            @RequestParam(value = "lockerSection")
            @Pattern(regexp = "^[A-I]$", message = "사물함 구역은 A부터 I까지 가능합니다.")
            String lockerSection
    ) {
        List<LockerResponseDTO.LockerInfoDTO> allLockers = lockerQueryService.findAllLockers(lockerSection, authMemberId);
        return ApiResponse.onSuccess(allLockers);
    }

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "나의 사물함 조회")
    @GetMapping("/me")
    public ApiResponse<LockerResponseDTO.MyLockerInfoDTO> getMyLocker(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        LockerResponseDTO.MyLockerInfoDTO myLocker = lockerQueryService.findMyLocker(authMemberId);
        return ApiResponse.onSuccess(myLocker);
    }

    @PreAuthorize("hasRole(\"USER\")")
    @Operation(summary = "사물함 신청")
    @PostMapping("/apply")
    public ApiResponse<Void> applyLocker(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        myLockerCommandService.applyLocker(authMemberId);
        return ApiResponse.onSuccess(null);
    }
}
