package hongik.heavyYoung.domain.item.repository;

import hongik.heavyYoung.domain.item.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
}
