package hongik.heavyYoung.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ItemResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemCategoryInfo {
        @Schema(description = "카테고리 아이디")
        private Long itemCategoryId;
        @Schema(description = "이름")
        private String itemCategoryName;
        @Schema(description = "총 수량")
        private int totalCount;
        @Schema(description = "대여 가능 수량")
        private int availableCount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemList {
        private List<ItemCategoryInfo> itemCategoryInfos;
    }
}
