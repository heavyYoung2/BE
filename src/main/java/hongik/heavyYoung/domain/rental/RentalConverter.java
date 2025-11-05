package hongik.heavyYoung.domain.rental;

import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;
import hongik.heavyYoung.domain.rental.entity.ItemRentalHistory;
import hongik.heavyYoung.domain.rental.enums.RentalStatus;

import java.time.LocalDate;
import java.util.List;

public class RentalConverter {
    public static RentalResponseDTO.MemberRentalInfo toMemberRentalInfo(
            LocalDate expectedBlacklistUntil,
            List<RentalResponseDTO.RentalItemInfo> rentalItemInfos
    ) {

        return RentalResponseDTO.MemberRentalInfo.builder()
                .expectedBlacklistUntil(expectedBlacklistUntil)
                .items(rentalItemInfos)
                .build();
    }

    public static RentalResponseDTO.RentalItemInfo toRentalItemInfo(ItemRentalHistory itemRentalHistory, String itemName, RentalStatus rentalStatus) {

        return RentalResponseDTO.RentalItemInfo.builder()
                .rentalHistoryId(itemRentalHistory.getId())
                .itemName(itemName)
                .rentalStartedAt(LocalDate.from(itemRentalHistory.getRentalStartAt()))
                .rentalEndedAt(LocalDate.from(itemRentalHistory.getExpectedReturnAt()))
                .rentalStatus(rentalStatus)
                .build();
    }

}
