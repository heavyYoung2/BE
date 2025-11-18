package hongik.heavyYoung.domain.studentFee.service.admin;

import hongik.heavyYoung.domain.studentFee.dto.StudentFeeRequestDTO;
import hongik.heavyYoung.domain.studentFee.dto.StudentFeeResponseDTO;

public interface StudentFeeAdminQueryService {
    StudentFeeResponseDTO verifyStudentFeePaymentByQrToken(StudentFeeRequestDTO studentFeeRequestDTO);

}
