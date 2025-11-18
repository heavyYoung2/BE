package hongik.heavyYoung.domain.item.service.admin.impl;

import hongik.heavyYoung.domain.item.converter.ItemCategoryConverter;
import hongik.heavyYoung.domain.item.dto.ItemCategoryRequestDTO;
import hongik.heavyYoung.domain.item.dto.ItemCategoryResponseDTO;
import hongik.heavyYoung.domain.item.entity.ItemCategory;
import hongik.heavyYoung.domain.item.repository.ItemCategoryRepository;
import hongik.heavyYoung.domain.item.service.admin.AdminItemCategoryCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminItemCategoryCommandServiceImpl implements AdminItemCategoryCommandService {

    private final ItemCategoryRepository itemCategoryRepository;

    @Override
    public ItemCategoryResponseDTO.Create createItemCategory(ItemCategoryRequestDTO.Create request) {
        ItemCategory itemCategory = ItemCategoryConverter.toItemCategory(request);

        itemCategoryRepository.save(itemCategory);

        return ItemCategoryConverter.toItemCategoryCreate(itemCategory);
    }
}
