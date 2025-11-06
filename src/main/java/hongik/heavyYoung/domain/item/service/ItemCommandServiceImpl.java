package hongik.heavyYoung.domain.item.service;

import hongik.heavyYoung.domain.item.converter.ItemConverter;
import hongik.heavyYoung.domain.item.dto.ItemRequestDTO;
import hongik.heavyYoung.domain.item.entity.Item;
import hongik.heavyYoung.domain.item.entity.ItemCategory;
import hongik.heavyYoung.domain.item.repository.ItemCategoryRepository;
import hongik.heavyYoung.domain.item.repository.ItemRepository;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.ItemCategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemCommandServiceImpl implements ItemCommandService {

    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    @Override
    public void increaseItemQuantity(ItemRequestDTO.Increase request) {
        // 카테고리 아이디
        ItemCategory itemCategory = itemCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ItemCategoryException(ErrorStatus.ITEM_CATEGORY_NOT_FOUND));

        // 아이템 생성하면서, 해당 카테고리 아이디에 연결
        Item item = ItemConverter.toItem(itemCategory);

        // 아이템 저장
        itemRepository.save(item);

        // 카테고리 총 수량, 가용 수량 +1
        itemCategory.increaseQuantity();
    }
}
