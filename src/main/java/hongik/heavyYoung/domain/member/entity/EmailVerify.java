package hongik.heavyYoung.domain.member.entity;

import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "email_verify")
public class EmailVerify extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_address", nullable = false, length = 100)
    private String emailAddress;

    @Column(name = "code", nullable = false, length = 10)
    private String code;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    public void updateCode(String newCode) {
        this.code = newCode;
        this.verified = false;
    }

    public void updateVerified(boolean verified) {
        this.verified = verified;
    }
}
