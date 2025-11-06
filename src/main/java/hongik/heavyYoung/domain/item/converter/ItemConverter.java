package hongik.heavyYoung.domain.item.converter;

import hongik.heavyYoung.domain.item.dto.ItemResponseDTO;
import hongik.heavyYoung.domain.item.entity.Item;
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
    }

    public static Item toItem(ItemCategory itemCategory) {

        return Item.builder()
                .itemCategory(itemCategory)
                .build();
    }

    public static ItemResponseDTO.ItemInfo toItemInfo(Item item) {

        return ItemResponseDTO.ItemInfo.builder()
                .ItemId(item.getId())
                .rented(item.isRented())
                .CategoryName(item.getItemCategory().getItemCategoryName())
                .build();
    }

    public static ItemResponseDTO.ItemListByCategory toItemListByCategory(List<Item> items) {
        List<ItemResponseDTO.ItemInfo> itemListByCategories = items.stream()
                .map(ItemConverter::toItemInfo)
                .toList();

        return ItemResponseDTO.ItemListByCategory.builder()
                .items(itemListByCategories)
                .build();
    }
}
