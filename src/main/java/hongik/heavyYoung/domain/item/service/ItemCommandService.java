package hongik.heavyYoung.domain.item.service;

import hongik.heavyYoung.domain.item.dto.ItemRequestDTO;

public interface ItemCommandService {
    void increaseItemQuantity(ItemRequestDTO.Increase request);
}
