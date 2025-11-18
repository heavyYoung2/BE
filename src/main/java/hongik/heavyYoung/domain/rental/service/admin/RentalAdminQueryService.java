package hongik.heavyYoung.domain.rental.service.admin;

import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;

public interface RentalAdminQueryService {
    RentalResponseDTO.AllRentalHistories getAllRentalHistories();
}
