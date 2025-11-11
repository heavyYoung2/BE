package hongik.heavyYoung.domain.rental.controller;

import hongik.heavyYoung.domain.rental.dto.RentalRequestDTO;
import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;
import hongik.heavyYoung.domain.rental.service.RentalAdminCommandService;
import hongik.heavyYoung.domain.rental.service.RentalAdminQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Rental API - 학생회", description = "학생회 - 대여 관련 API")
@RestController
@RequestMapping("/admin/rentals")
@RequiredArgsConstructor
public class RentalAdminRestController {

    private final RentalAdminCommandService rentalAdminCommandService;
    private final RentalAdminQueryService rentalAdminQueryService;

    @Operation(summary = "물품 대여 QR 스캔")
    @PostMapping("/qr")
    public ApiResponse<?> rentalByQr(@RequestBody RentalRequestDTO.RentalQrToken request) {
        rentalAdminCommandService.rentalByQr(request);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "대여 전체 현황 조회")
    @GetMapping("")
    private ApiResponse<RentalResponseDTO.AllRentalHistories> getAllRentalHistories() {
        return ApiResponse.onSuccess(rentalAdminQueryService.getAllRentalHistories());
    }
}
