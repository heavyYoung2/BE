// src/main/java/com/yourapp/domain/item/entity/ItemCategory.java
package hongik.heavyYoung.domain.item.entity;

import hongik.heavyYoung.domain.item.enums.ItemCategoryName;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "item_category_name", nullable = false, length = 20)
    private ItemCategoryName itemCategoryName;

    @Column(name = "total_count", nullable = false)
    @Builder.Default
    private int totalCount = 0;

    @Column(name = "available_count", nullable = false)
    @Builder.Default
    private int availableCount = 0;
}