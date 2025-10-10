package hongik.heavyYoung.domain.locker.controller.general;

import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.service.general.LockerQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lockers")
@Tag(name = "Locker API - 학생", description = "학생 - 사물함 관련 API")
@Validated
@RequiredArgsConstructor
public class LockerRestController {

    private final LockerQueryService lockerQueryService;

    @Operation(summary = "사물함 전체 조회")
    @GetMapping
    public ApiResponse<List<LockerResponse.LockerInfoDTO>> getAllLockers(
            @Parameter(description = "사물함 구역", example = "A", required = true)
            @RequestParam(value = "lockerSection")
            @Pattern(regexp = "^[A-I]$", message = "사물함 구역은 A부터 I까지 가능합니다.")
            String lockerSection
    ) {
        List<LockerResponse.LockerInfoDTO> allLockers = lockerQueryService.findAllLockers(lockerSection);
        return ApiResponse.onSuccess(allLockers);
    }

    @Operation(summary = "나의 사물함 조회")
    @GetMapping("/me")
    public ApiResponse<LockerResponse.MyLockerInfoDTO> getMyLocker() {
        LockerResponse.MyLockerInfoDTO myLocker = lockerQueryService.findMyLocker();
        return ApiResponse.onSuccess(myLocker);
    }
}
