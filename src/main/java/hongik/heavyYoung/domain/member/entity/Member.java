package hongik.heavyYoung.domain.member.entity;

import hongik.heavyYoung.domain.member.enums.MemberRole;
import hongik.heavyYoung.domain.member.enums.MemberStatus;
import hongik.heavyYoung.domain.member.enums.StudentFeeStatus;
import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(name = "student_name", nullable = false, length = 20)
    private String studentName;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_fee_status", nullable = false, length = 20)
    @Builder.Default
    private StudentFeeStatus studentFeeStatus = StudentFeeStatus.YET;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    @Builder.Default
    private MemberRole role = MemberRole.USER;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "blacklist_until")
    private LocalDate blacklistUntil;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_status", nullable = false, length = 50)
    @Builder.Default
    private MemberStatus memberStatus = MemberStatus.PENDING;
}