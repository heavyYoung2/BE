package hongik.heavyYoung.domain.rental.service.admin;

import hongik.heavyYoung.domain.rental.dto.RentalRequestDTO;

public interface RentalAdminCommandService {
    void rentalByQr(RentalRequestDTO.QrToken qrToken);
    void returnByQr(RentalRequestDTO.QrToken request);
    void manualReturn(Long rentalHistoryId);
}
