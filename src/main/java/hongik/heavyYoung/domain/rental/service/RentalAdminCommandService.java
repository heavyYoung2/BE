package hongik.heavyYoung.domain.rental.service;

import hongik.heavyYoung.domain.rental.dto.RentalRequestDTO;

public interface RentalAdminCommandService {
    void rentalByQr(RentalRequestDTO.RentalQrToken rentalQrToken);
}
