package hongik.heavyYoung.domain.rental.service;

import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;
import hongik.heavyYoung.global.qr.QrTokenResponse;

public interface RentalQueryService {
    QrTokenResponse generateRentalQrToken(Long itemCategoryId);
    QrTokenResponse generateReturnRentalQrToken(Long rentalHistoryId);
    RentalResponseDTO.MemberRentalInfo getRentalStatus();
    RentalResponseDTO.RentalHistoryInfo getRentalHistory();
}
