package hongik.heavyYoung.domain.member;

import hongik.heavyYoung.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
