package hongik.heavyYoung.domain.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RentalRequestDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QrToken {
        private Long itemCategoryId;
        private String qrToken;
    }
}
