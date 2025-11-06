package hongik.heavyYoung.domain.item.converter;

import hongik.heavyYoung.domain.item.dto.ItemResponseDTO;
import hongik.heavyYoung.domain.item.entity.ItemCategory;

import java.util.List;

public class ItemConverter {

    public static ItemResponseDTO.ItemCategoryInfo toItemCategoryInfo(ItemCategory itemCategory) {

        return ItemResponseDTO.ItemCategoryInfo.builder()
                .itemCategoryId(itemCategory.getId())
                .itemCategoryName(itemCategory.getItemCategoryName())
                .totalCount(itemCategory.getTotalCount())
                .availableCount(itemCategory.getAvailableCount())
                .build();
    }

    public static ItemResponseDTO.ItemList toItemCategoryList(List<ItemCategory> itemCategories) {
        List<ItemResponseDTO.ItemCategoryInfo> infos = itemCategories.stream()
                .map(ItemConverter::toItemCategoryInfo)
                .toList();

        return ItemResponseDTO.ItemList.builder()
                .itemCategoryInfos(infos)
                .build();
    }}
