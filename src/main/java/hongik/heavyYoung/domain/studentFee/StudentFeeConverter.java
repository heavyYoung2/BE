package hongik.heavyYoung.domain.studentFee;

import hongik.heavyYoung.domain.studentFee.dto.StudentFeeResponseDTO;

public class StudentFeeConverter {
    public static StudentFeeResponseDTO toStudentFeeResponseDTO(boolean isStudentFeePaid) {

        return StudentFeeResponseDTO.builder()
                .approved(isStudentFeePaid)
                .build();
    }
}
