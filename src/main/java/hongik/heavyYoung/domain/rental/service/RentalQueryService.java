package hongik.heavyYoung.domain.rental.service;

import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;
import hongik.heavyYoung.global.qr.QrTokenResponse;

public interface RentalQueryService {
    QrTokenResponse generateRentalQrToken(Long authMemberId, Long itemCategoryId);
    QrTokenResponse generateReturnRentalQrToken(Long authMemberId, Long rentalHistoryId);
    RentalResponseDTO.MemberRentalInfo getRentalStatus(Long authMemberId);
    RentalResponseDTO.AllRentalHistories getRentalHistory(Long authMemberId);
}
