package hongik.heavyYoung.domain.item.service.admin;

import hongik.heavyYoung.domain.item.dto.ItemCategoryRequestDTO;
import hongik.heavyYoung.domain.item.dto.ItemCategoryResponseDTO;

public interface AdminItemCategoryCommandService {
    ItemCategoryResponseDTO.Create createItemCategory(ItemCategoryRequestDTO.Create itemCategory);
}
