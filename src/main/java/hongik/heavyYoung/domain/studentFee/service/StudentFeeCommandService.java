package hongik.heavyYoung.domain.studentFee.service;

import hongik.heavyYoung.global.qr.QrTokenResponse;

public interface StudentFeeCommandService {
    QrTokenResponse generateStudentFeeQrToken();
}
