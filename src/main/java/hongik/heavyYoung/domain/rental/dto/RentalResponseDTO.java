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
        private List<RentalHistory> items;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AllRentalHistories {
        private List<RentalHistory> items;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RentalHistory {
        private Long rentalHistoryId;
        private Long itemCategoryId;
        private String itemName;
        private String studentId;
        private String studentName;
        private LocalDate rentalStartedAt;
        private LocalDate rentalEndedAt;
        private LocalDate returnedAt;
        private RentalStatus rentalStatus;
    }

}
