package hongik.heavyYoung.domain.studentFee.service.admin.impl;

import hongik.heavyYoung.domain.studentFee.converter.StudentFeeConverter;
import hongik.heavyYoung.domain.studentFee.dto.StudentFeeRequestDTO;
import hongik.heavyYoung.domain.studentFee.dto.StudentFeeResponseDTO;
import hongik.heavyYoung.domain.studentFee.service.admin.StudentFeeAdminQueryService;
import hongik.heavyYoung.global.qr.QrManager;
import hongik.heavyYoung.global.qr.QrType;
import hongik.heavyYoung.global.qr.payload.StudentFeePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentFeeAdminQueryServiceImpl implements StudentFeeAdminQueryService {

    private final QrManager qrManager;

    @Override
    public StudentFeeResponseDTO verifyStudentFeePaymentByQrToken(StudentFeeRequestDTO studentFeeRequestDTO) {

        // 요청에서 QR토큰 가져오기
        String qrToken = studentFeeRequestDTO.getQrToken();

        // qrPayload 가져오기
        StudentFeePayload qrPayload = (StudentFeePayload) qrManager.decodeQrToken(QrType.STUDENT_FEE, qrToken);

        return StudentFeeConverter.toStudentFeeResponseDTO(qrPayload.isFeePaid());
    }
}
