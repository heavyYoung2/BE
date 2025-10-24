package hongik.heavyYoung.domain.studentFee.service;

import hongik.heavyYoung.global.qr.QrTokenResponseDTO;

public interface StudentFeeQueryService {
    QrTokenResponseDTO generateStudentFeeQrToken();
}
