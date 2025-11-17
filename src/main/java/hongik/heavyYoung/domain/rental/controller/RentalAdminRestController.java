package hongik.heavyYoung.domain.rental.controller;

import hongik.heavyYoung.domain.rental.dto.RentalRequestDTO;
import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;
import hongik.heavyYoung.domain.rental.service.RentalAdminCommandService;
import hongik.heavyYoung.domain.rental.service.RentalAdminQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Rental API - 학생회", description = "학생회 - 대여 관련 API")
@RestController
@RequestMapping("/admin/rentals")
@RequiredArgsConstructor
public class RentalAdminRestController {

    private final RentalAdminCommandService rentalAdminCommandService;
    private final RentalAdminQueryService rentalAdminQueryService;

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "물품 대여 QR 스캔")
    @PostMapping("/qr")
    public ApiResponse<?> rentalByQr(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @RequestBody RentalRequestDTO.QrToken request
    ) {
        rentalAdminCommandService.rentalByQr(request);
        return ApiResponse.onSuccess(null);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "대여 전체 현황 조회")
    @GetMapping("")
    public ApiResponse<RentalResponseDTO.AllRentalHistories> getAllRentalHistories(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId
    ) {
        return ApiResponse.onSuccess(rentalAdminQueryService.getAllRentalHistories());
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "물품 반납 QR 스캔")
    @PostMapping("/return")
    public ApiResponse<?> returnByQr(
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @RequestBody RentalRequestDTO.QrToken request
    ) {
        rentalAdminCommandService.returnByQr(request);
        return ApiResponse.onSuccess(null);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @Operation(summary = "물품 수동 반납")
    @PostMapping("/{rental-history-id}/return")
    public ApiResponse<?> manualReturn (
            @Parameter(hidden = true) @AuthMemberId Long authMemberId,
            @PathVariable("rental-history-id") Long rentalHistoryId
    ) {
        rentalAdminCommandService.manualReturn(rentalHistoryId);
        return ApiResponse.onSuccess(null);
    }
}
