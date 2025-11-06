package hongik.heavyYoung.domain.item.service;

import hongik.heavyYoung.domain.item.converter.ItemConverter;
import hongik.heavyYoung.domain.item.dto.ItemResponseDTO;
import hongik.heavyYoung.domain.item.entity.Item;
import hongik.heavyYoung.domain.item.entity.ItemCategory;
import hongik.heavyYoung.domain.item.repository.ItemCategoryRepository;
import hongik.heavyYoung.domain.item.repository.ItemRepository;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.ItemCategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemQueryServiceImpl implements ItemQueryService {

    private final ItemCategoryRepository itemCategoryRepository;
    private final ItemRepository itemRepository;

    /**
     * 대여 가능한 물품 카테고리 목록을 조회한다.
     * 각 카테고리의 전체 수량과 대여 가능 수량 요약을 포함한다.
     *
     * @return {@link ItemResponseDTO.ItemList} : 카테고리별 요약 정보 리스트 DTO
     */
    @Override
    public ItemResponseDTO.ItemList getAllItems() {
        List<ItemCategory> itemCategories = itemCategoryRepository.findAll();

        return ItemConverter.toItemCategoryList(itemCategories);
    }

    @Override
    public ItemResponseDTO.ItemListByCategory getItemListByCategoryId(Long itemCategoryId) {
        ItemCategory itemCategory = itemCategoryRepository.findById(itemCategoryId)
                .orElseThrow(() -> new ItemCategoryException(ErrorStatus.ITEM_CATEGORY_NOT_FOUND));

        List<Item> items = itemRepository.findAllByItemCategory(itemCategory);

        return ItemConverter.toItemListByCategory(items);
    }
}
