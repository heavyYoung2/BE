package hongik.heavyYoung.domain.member.repository;

import hongik.heavyYoung.domain.member.entity.EmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
    Optional<EmailVerify> findByEmailAddress(String email);
    boolean existsByEmailAddress(String emailAddress);
    void deleteByEmailAddress(String emailAddress);
}
