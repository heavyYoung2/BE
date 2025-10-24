package hongik.heavyYoung.global.qr;

import hongik.heavyYoung.domain.studentFee.dto.StudentFeeResponseDTO;

public class QrConverter {

    public static QrTokenResponseDTO toQrTokenResponseDTO(String qrToken, boolean isStudentFeePaid) {
        return QrTokenResponseDTO.builder()
                .qrToken(qrToken)
                .isStudentFeePaid(isStudentFeePaid)
                .build();
    }

    public static StudentFeeResponseDTO toStudentFeeResponseDTO(boolean isStudentFeePaid) {
        return StudentFeeResponseDTO.builder()
                .isApproved(isStudentFeePaid)
                .build();
    }
}
