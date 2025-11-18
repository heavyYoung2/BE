package hongik.heavyYoung.domain.item.service.admin;

import hongik.heavyYoung.domain.item.dto.ItemRequestDTO;

public interface AdminItemCommandService {
    void increaseItemQuantity(ItemRequestDTO.Increase request);
    void deleteItem(Long itemId);
}
