package hongik.heavyYoung.domain.rental.converter;

import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;
import hongik.heavyYoung.domain.rental.entity.ItemRentalHistory;
import hongik.heavyYoung.domain.rental.enums.RentalStatus;

import java.time.LocalDate;
import java.util.List;

public class RentalConverter {
    public static RentalResponseDTO.MemberRentalInfo toRentalInfo(
            LocalDate expectedBlacklistUntil,
            List<RentalResponseDTO.RentalHistory> rentalHistories) {

        return RentalResponseDTO.MemberRentalInfo.builder()
                .expectedBlacklistUntil(expectedBlacklistUntil)
                .items(rentalHistories)
                .build();
    }

    public static RentalResponseDTO.AllRentalHistories toAllRentalHistories(
            List<RentalResponseDTO.RentalHistory> rentalHistories) {

        return RentalResponseDTO.AllRentalHistories.builder()
                .items(rentalHistories)
                .build();
    }

    public static RentalResponseDTO.RentalHistory toRentalHistory(
            ItemRentalHistory itemRentalHistory,
            RentalStatus rentalStatus) {

        return RentalResponseDTO.RentalHistory.builder()
                .rentalHistoryId(itemRentalHistory.getId())
                .itemName(itemRentalHistory.getItem().getItemCategory().getItemCategoryName())
                .rentalStartedAt(LocalDate.from(itemRentalHistory.getRentalStartAt()))
                .rentalEndedAt(LocalDate.from(itemRentalHistory.getExpectedReturnAt()))
                .returnedAt(itemRentalHistory.getReturnedAt() != null ? LocalDate.from(itemRentalHistory.getReturnedAt()) : null)
                .rentalStatus(rentalStatus)
                .build();
    }
}
