package hongik.heavyYoung.domain.rental.service;

import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;

public interface RentalAdminQueryService {
    RentalResponseDTO.AllRentalHistories getAllRentalHistories();
}
