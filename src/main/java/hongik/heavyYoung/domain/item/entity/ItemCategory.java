// src/main/java/com/yourapp/domain/item/entity/ItemCategory.java
package hongik.heavyYoung.domain.item.entity;

import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "item_category")
public class ItemCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_category_id")
    private Long id;

    @Column(name = "item_category_name", nullable = false, length = 100)
    private String itemCategoryName;

    @Column(name = "total_count", nullable = false)
    @Builder.Default
    private int totalCount = 0;

    @Column(name = "available_count", nullable = false)
    @Builder.Default
    private int availableCount = 0;

    public void increaseQuantity() {
        this.totalCount++;
        this.availableCount++;
    }

    public void decreaseQuantity() {
        this.totalCount--;
        this.availableCount--;
    }
}