package hongik.heavyYoung.domain.item.repository;

import hongik.heavyYoung.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Integer> {

}
