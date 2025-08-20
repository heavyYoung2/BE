package hongik.heavyYoun.domain.locker.entity;

import hongik.heavyYoun.domain.member.entity.Member;
import hongik.heavyYoun.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "member_locker")
public class MemberLocker extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_locker_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locker_id", nullable = false)
    private Locker locker;

    @Column(name = "semester", nullable = false, length = 20)
    private String semester;

    @Column(name = "is_current_semester", nullable = false)
    @Builder.Default
    private Boolean isCurrentSemester = true;
}