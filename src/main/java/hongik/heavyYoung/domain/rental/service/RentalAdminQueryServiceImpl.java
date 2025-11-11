package hongik.heavyYoung.domain.rental.service;

import hongik.heavyYoung.domain.rental.converter.RentalConverter;
import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;
import hongik.heavyYoung.domain.rental.entity.ItemRentalHistory;
import hongik.heavyYoung.domain.rental.enums.RentalStatus;
import hongik.heavyYoung.domain.rental.repository.ItemRentalHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RentalAdminQueryServiceImpl implements RentalAdminQueryService {

    private final ItemRentalHistoryRepository itemRentalHistoryRepository;

    @Override
    public RentalResponseDTO.AllRentalHistories getAllRentalHistories() {

        // 전체 대여 내역 가져오기
        List<ItemRentalHistory> itemRentalHistoryList = itemRentalHistoryRepository.findAllHistories();

        List<RentalResponseDTO.RentalHistory> rentalHistories = new ArrayList<>();
        for (ItemRentalHistory itemRentalHistory : itemRentalHistoryList) {
            RentalStatus rentalStatus = resolveStatus(itemRentalHistory, LocalDateTime.now());
            RentalResponseDTO.RentalHistory rentalHistory = RentalConverter.toRentalHistory(itemRentalHistory, rentalStatus);
            rentalHistories.add(rentalHistory);
        }

        return RentalConverter.toAllRentalHistories(rentalHistories);
    }

    // 대여 상태 계산
    private RentalStatus resolveStatus(ItemRentalHistory itemRentalHistory, LocalDateTime now) {
        if (itemRentalHistory.getReturnedAt() != null) {
            return RentalStatus.RETURNED;
        } else if (itemRentalHistory.getExpectedReturnAt().isBefore(now)) {
            return RentalStatus.OVERDUE;
        } else {
            return RentalStatus.RENTING;
        }
    }
}
