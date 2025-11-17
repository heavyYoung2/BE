package hongik.heavyYoung.domain.member.repository;

import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
    List<Member> findAllByRoleIn(Collection<MemberRole> roles);
    Optional<Member> findByStudentId(String studentId);
}
