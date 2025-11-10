package hongik.heavyYoung.domain.item.repository;

import hongik.heavyYoung.domain.item.entity.Item;
import hongik.heavyYoung.domain.item.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByItemCategory(ItemCategory itemCategory);
}
