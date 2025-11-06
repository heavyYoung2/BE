package hongik.heavyYoung.domain.locker.repository;

import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LockerAssignmentRepository extends JpaRepository<LockerAssignment, Long> {
    Optional<LockerAssignment> findByMember_IdAndIsCurrentSemesterTrue(Long memberId);
}
