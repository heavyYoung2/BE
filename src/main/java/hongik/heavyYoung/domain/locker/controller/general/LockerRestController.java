package hongik.heavyYoung.domain.locker.controller.general;

import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.service.general.LockerQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lockers")
@Tag(name = "Locker API - 학생", description = "학생 - 사물함 관련 API")
@RequiredArgsConstructor
public class LockerRestController {

    private final LockerQueryService lockerQueryService;

    @Operation(summary = "사물함 전체 조회")
    @GetMapping
    public ApiResponse<List<LockerResponse.LockerInfoDTO>> getAllLockers(
            @Parameter(description = "사물함 구역", example = "A")
            @RequestParam(value = "lockerSection")
            String lockerSection
    ) {
        List<LockerResponse.LockerInfoDTO> allLockers = lockerQueryService.findAllLockers(lockerSection);
        return ApiResponse.onSuccess(allLockers);
    }
}
