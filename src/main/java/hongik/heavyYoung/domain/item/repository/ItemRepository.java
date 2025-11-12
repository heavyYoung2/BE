package hongik.heavyYoung.domain.item.repository;

import hongik.heavyYoung.domain.item.entity.Item;
import hongik.heavyYoung.domain.item.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findFirstByItemCategoryIdAndIsRentedFalseOrderByIdAsc(Long itemCategoryId);
    List<Item> findAllByItemCategory(ItemCategory itemCategory);
}
