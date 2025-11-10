package hongik.heavyYoung.domain.item.service;

import hongik.heavyYoung.domain.item.dto.ItemCategoryRequestDTO;
import hongik.heavyYoung.domain.item.dto.ItemCategoryResponseDTO;

public interface ItemCategoryCommandService {
    ItemCategoryResponseDTO.Create createItemCategory(ItemCategoryRequestDTO.Create itemCategory);
}
