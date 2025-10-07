package hongik.heavyYoung.domain.locker.repository;

import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockerAssignmentRepository extends JpaRepository<LockerAssignment, Long> {
}
