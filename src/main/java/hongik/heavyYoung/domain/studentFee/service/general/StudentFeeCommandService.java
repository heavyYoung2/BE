package hongik.heavyYoung.domain.studentFee.service.general;

import hongik.heavyYoung.global.qr.QrTokenResponse;

public interface StudentFeeCommandService {
    QrTokenResponse generateStudentFeeQrToken(Long authMemberId);
}
