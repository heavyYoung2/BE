package hongik.heavyYoung.domain.item.service.admin.impl;

import hongik.heavyYoung.domain.item.converter.ItemConverter;
import hongik.heavyYoung.domain.item.dto.ItemRequestDTO;
import hongik.heavyYoung.domain.item.entity.Item;
import hongik.heavyYoung.domain.item.entity.ItemCategory;
import hongik.heavyYoung.domain.item.repository.ItemCategoryRepository;
import hongik.heavyYoung.domain.item.repository.ItemRepository;
import hongik.heavyYoung.domain.item.service.admin.AdminItemCommandService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.ItemCategoryException;
import hongik.heavyYoung.global.exception.customException.ItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminItemCommandServiceImpl implements AdminItemCommandService {

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
        itemCategory.increaseTotalCount();
        itemCategory.increaseAvailableCount();
    }

    @Override
    public void deleteItem(Long itemId) {
        // 아이템 가져오기
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemException(ErrorStatus.ITEM_NOT_FOUND));

        // 대여중이라면 반납 후 삭제
        if (item.isRented()) throw new ItemException(ErrorStatus.ITEM_NOT_FOUND);

        // 해당 카테고리 수량 감소
        ItemCategory itemCategory = item.getItemCategory();
        itemCategory.decreaseTotalCount();
        itemCategory.decreaseAvailableCount();

        // 아이템 삭제
        itemRepository.delete(item);
    }
}
