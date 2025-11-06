package hongik.heavyYoung.domain.rental;

import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;
import hongik.heavyYoung.domain.rental.entity.ItemRentalHistory;
import hongik.heavyYoung.domain.rental.enums.RentalStatus;

import java.time.LocalDate;
import java.util.List;

public class RentalConverter {
    public static RentalResponseDTO.MemberRentalInfo toRentalInfo(
            LocalDate expectedBlacklistUntil,
            List<RentalResponseDTO.RentalItemInfo> rentalItemInfos) {

        return RentalResponseDTO.MemberRentalInfo.builder()
                .expectedBlacklistUntil(expectedBlacklistUntil)
                .items(rentalItemInfos)
                .build();
    }

    public static RentalResponseDTO.RentalItemInfo toRentalItemInfo(
            ItemRentalHistory itemRentalHistory,
            RentalStatus rentalStatus) {

        return RentalResponseDTO.RentalItemInfo.builder()
                .rentalHistoryId(itemRentalHistory.getId())
                .itemName(itemRentalHistory.getItem().getItemCategory().getItemCategoryName())
                .rentalStartedAt(LocalDate.from(itemRentalHistory.getRentalStartAt()))
                .rentalEndedAt(LocalDate.from(itemRentalHistory.getExpectedReturnAt()))
                .rentalStatus(rentalStatus)
                .build();
    }

    public static RentalResponseDTO.RentalHistoryInfo toRentalHistoryInfo(
            List<RentalResponseDTO.RentalItemHistoryInfo> rentalItemHistoryInfos) {

        return RentalResponseDTO.RentalHistoryInfo.builder()
                .items(rentalItemHistoryInfos)
                .build();
    }

    public static RentalResponseDTO.RentalItemHistoryInfo toRentalItemHistoryInfo(
            ItemRentalHistory itemRentalHistory,
            RentalStatus rentalStatus) {

        return RentalResponseDTO.RentalItemHistoryInfo.builder()
                .rentalHistoryId(itemRentalHistory.getId())
                .itemName(itemRentalHistory.getItem().getItemCategory().getItemCategoryName())
                .rentalStartedAt(LocalDate.from(itemRentalHistory.getRentalStartAt()))
                .returnedAt(itemRentalHistory.getReturnedAt() != null ? LocalDate.from(itemRentalHistory.getReturnedAt()) : null)
                .rentalStatus(rentalStatus)
                .build();
    }

}
