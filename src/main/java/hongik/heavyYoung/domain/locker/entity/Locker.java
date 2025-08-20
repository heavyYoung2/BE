package hongik.heavyYoung.domain.locker.entity;

import hongik.heavyYoung.domain.locker.enums.LockerStatus;
import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "locker")
public class Locker extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_id")
    private Long id;

    @Column(name = "locker_number", nullable = false, length = 20)
    private String lockerNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "locker_status", nullable = false, length = 20)
    @Builder.Default
    private LockerStatus lockerStatus = LockerStatus.AVAILABLE;

    @Column(name = "available_locker_count", nullable = false)
    private Integer availableLockerCount;
}