package hongik.heavyYoung.domain.item.service;

import hongik.heavyYoung.domain.item.converter.ItemConverter;
import hongik.heavyYoung.domain.item.dto.ItemResponseDTO;
import hongik.heavyYoung.domain.item.entity.ItemCategory;
import hongik.heavyYoung.domain.item.repository.ItemCategoryRepository;
import hongik.heavyYoung.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemQueryServiceImpl implements ItemQueryService {

    final ItemCategoryRepository itemCategoryRepository;

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
}
