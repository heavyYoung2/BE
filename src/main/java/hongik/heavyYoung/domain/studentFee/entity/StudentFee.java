package hongik.heavyYoung.domain.studentFee.entity;

import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "student_fee")
public class StudentFee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_fee_id")
    private Long id;

    @Column(name = "student_name", nullable = false, length = 20)
    private String studentName;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(name = "is_fee_paid", nullable = false)
    @Builder.Default
    private boolean isFeePaid = false;
}