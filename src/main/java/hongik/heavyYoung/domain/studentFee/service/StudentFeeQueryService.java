package hongik.heavyYoung.domain.studentFee.service;

import hongik.heavyYoung.global.qr.QrTokenResponse;

public interface StudentFeeQueryService {
    QrTokenResponse generateStudentFeeQrToken();
}
