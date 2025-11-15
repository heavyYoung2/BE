package hongik.heavyYoung.domain.rental.controller;

import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;
import hongik.heavyYoung.domain.rental.service.RentalQueryService;
import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.security.auth.AuthMemberId;
import hongik.heavyYoung.global.qr.QrTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Rental API - 학생", description = "학생 - 대여 관련 API")
@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalRestController {

    private final RentalQueryService rentalQueryService;

    @Operation(summary = "물품 대여 QR 생성")
    @GetMapping("/{item-category-id}/qr-tokens")
    public ApiResponse<QrTokenResponse> generateRentalQrToken(
            @PathVariable("item-category-id") Long itemCategoryId
    ) {
        return ApiResponse.onSuccess(rentalQueryService.generateRentalQrToken(itemCategoryId));
    }

    @Operation(summary = "물품 반납 QR 생성")
    @GetMapping("/{rental-history-id}/return/qr-tokens")
    public ApiResponse<QrTokenResponse> generateReturnRentalQrToken(
            @PathVariable("rental-history-id") Long rentalHistoryId
    ) {
        return ApiResponse.onSuccess(rentalQueryService.generateReturnRentalQrToken(rentalHistoryId));
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "내 대여 현황 조회")
    @GetMapping("/me")
    public ApiResponse<RentalResponseDTO.MemberRentalInfo> getRentalStatus(@Parameter(hidden = true) @AuthMemberId Long memberId) {
        return ApiResponse.onSuccess(rentalQueryService.getRentalStatus(memberId));
    }

    @Operation(summary = "전체 대여 내역 조회")
    @GetMapping("/me/history")
    public ApiResponse<RentalResponseDTO.AllRentalHistories> getRentalHistory() {
        return ApiResponse.onSuccess(rentalQueryService.getRentalHistory());
    }
}
