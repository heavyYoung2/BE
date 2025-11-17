package hongik.heavyYoung.domain.item.converter;

import hongik.heavyYoung.domain.item.dto.ItemCategoryRequestDTO;
import hongik.heavyYoung.domain.item.dto.ItemCategoryResponseDTO;
import hongik.heavyYoung.domain.item.entity.ItemCategory;

public class ItemCategoryConverter {

    public static ItemCategory toItemCategory(ItemCategoryRequestDTO.Create request) {
        return ItemCategory.builder()
                .itemCategoryName(request.getCategoryName())
                .build();
    }

    public static ItemCategoryResponseDTO.Create toItemCategoryCreate(ItemCategory itemCategory) {
        return ItemCategoryResponseDTO.Create.builder()
                .categoryId(itemCategory.getId())
                .build();
    }
}
