package hongik.heavyYoung.domain.locker.entity;

import hongik.heavyYoung.domain.locker.enums.LockerApplicationPeriodType;
import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "locker_application_period")
public class LockerApplicationPeriod extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_application_period_id")
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "semester", nullable = false, length = 20)
    private String semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private LockerApplicationPeriodType type;

    @Column(name = "flag", nullable = false)
    @Builder.Default
    private Boolean flag = true;

    @Column(name = "can_assign", nullable = false)
    @Builder.Default
    private Boolean canAssign = true;
}