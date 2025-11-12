package hongik.heavyYoung.domain.studentFee.repository;

import hongik.heavyYoung.domain.studentFee.entity.StudentFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentFeeRepository extends JpaRepository<StudentFee, Long> {
    StudentFee findByStudentId(String studentId);
}
