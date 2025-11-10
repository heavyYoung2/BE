package hongik.heavyYoung.domain.item.service;

import hongik.heavyYoung.domain.item.dto.ItemResponseDTO;

public interface ItemQueryService {
    ItemResponseDTO.ItemList getAllItems();
    ItemResponseDTO.ItemListByCategory getItemListByCategoryId(Long itemCategoryId);
}
