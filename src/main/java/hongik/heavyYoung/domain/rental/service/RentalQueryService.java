package hongik.heavyYoung.domain.rental.service;

import hongik.heavyYoung.global.qr.QrTokenResponse;

public interface RentalQueryService {
    QrTokenResponse generateRentalQrToken(Long itemCategoryId);
    QrTokenResponse generateReturnRentalQrToken(Long rentalHistoryId);

}
