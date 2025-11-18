package hongik.heavyYoung.domain.studentFee.repository;

import hongik.heavyYoung.domain.studentFee.entity.StudentFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentFeeRepository extends JpaRepository<StudentFee, Long> {
    Optional<StudentFee> findByStudentId(String studentId);
}
