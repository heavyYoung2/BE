package hongik.heavyYoung.domain.rental.entity;

import hongik.heavyYoung.domain.item.entity.Item;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "item_rental_history")
public class ItemRentalHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_rental_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "rental_start_at", nullable = false)
    private LocalDateTime rentalStartAt;

    @Column(name = "expected_return_at", nullable = false)
    private LocalDateTime expectedReturnAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    public void updateReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }
}