package hongik.heavyYoung.domain.application.entity;

import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "application")
public class Application extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @Column(name = "application_start_at", nullable = false)
    private LocalDateTime applicationStartAt;

    @Column(name = "application_end_at", nullable = false)
    private LocalDateTime applicationEndAt;

    @Column(name = "application_semester", nullable = false, length = 20)
    private String applicationSemester;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_type", nullable = false, length = 20)
    private ApplicationType applicationType;

    @Column(name = "application_can_count", nullable = false)
    @Builder.Default
    private int applicationCanCount = 0;

    @Column(name = "application_member_count", nullable = false)
    @Builder.Default
    private int applicationMemberCount = 0;

    @Column(name = "can_apply", nullable = false)
    @Builder.Default
    private boolean canApply = true;

    @Column(name = "can_assign", nullable = false)
    @Builder.Default
    private boolean canAssign = true;

    public void updateCanAssignToFalse() {
        this.canAssign = false;
    }

    public void updateCanApplyToFalse() {
        this.canApply = false;
    }
}