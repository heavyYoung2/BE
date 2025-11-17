package hongik.heavyYoung.domain.locker.entity;

import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "locker_assignment")
public class LockerAssignment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_assignment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locker_id", nullable = false)
    private Locker locker;

    @Column(name = "assign_semester", nullable = false, length = 20)
    private String assignSemester;

    @Column(name = "is_current_semester", nullable = false)
    @Builder.Default
    private boolean isCurrentSemester = true;

    public void updateIsCurrentSemesterToFalse() {
        this.isCurrentSemester = false;
    }
}