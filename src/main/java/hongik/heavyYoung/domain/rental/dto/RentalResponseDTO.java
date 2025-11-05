package hongik.heavyYoung.domain.rental.dto;

import hongik.heavyYoung.domain.rental.enums.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class RentalResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberRentalInfo {
        private LocalDate expectedBlacklistUntil;
        private List<RentalItemInfo> items;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RentalItemInfo {
        private Long rentalHistoryId;
        private String itemName;
        private LocalDate rentalStartedAt;
        private LocalDate rentalEndedAt;
        private RentalStatus rentalStatus;
    }
}
