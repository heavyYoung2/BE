package hongik.heavyYoung.domain.locker.controller.general;

import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.service.general.LockerQueryService;
import hongik.heavyYoung.domain.locker.service.general.MyLockerCommandService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
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

    // TODO 로그인 구현 시 멤버아이디 전달하도록 수정
    private static final Long DUMMY_MEMBER_ID = 23L;

    @Operation(summary = "사물함 전체 조회")
    @GetMapping
    public ApiResponse<List<LockerResponse.LockerInfoDTO>> getAllLockers(
            @Parameter(description = "사물함 구역", example = "A", required = true)
            @RequestParam(value = "lockerSection")
            @Pattern(regexp = "^[A-I]$", message = "사물함 구역은 A부터 I까지 가능합니다.")
            String lockerSection
    ) {
        // TODO 로그인 구현 시 멤버아이디 전달하도록 수정
        List<LockerResponse.LockerInfoDTO> allLockers = lockerQueryService.findAllLockers(lockerSection, DUMMY_MEMBER_ID);
        return ApiResponse.onSuccess(allLockers);
    }

    @Operation(summary = "나의 사물함 조회")
    @GetMapping("/me")
    public ApiResponse<LockerResponse.MyLockerInfoDTO> getMyLocker() {
        // TODO 로그인 구현 시 멤버아이디 전달하도록 수정
        LockerResponse.MyLockerInfoDTO myLocker = lockerQueryService.findMyLocker(DUMMY_MEMBER_ID);
        return ApiResponse.onSuccess(myLocker);
    }

    @Operation(summary = "사물함 신청")
    @PostMapping("/apply")
    public ApiResponse<Void> applyLocker() {
        myLockerCommandService.applyLocker(DUMMY_MEMBER_ID);
        return ApiResponse.onSuccess(null);
    }
}
