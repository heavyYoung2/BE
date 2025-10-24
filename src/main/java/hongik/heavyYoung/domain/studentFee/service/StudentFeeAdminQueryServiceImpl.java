package hongik.heavyYoung.domain.studentFee.service;

import hongik.heavyYoung.domain.studentFee.dto.StudentFeeRequestDTO;
import hongik.heavyYoung.domain.studentFee.dto.StudentFeeResponseDTO;
import hongik.heavyYoung.global.qr.QrConverter;
import hongik.heavyYoung.global.qr.QrManager;
import hongik.heavyYoung.global.qr.QrType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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
        Map<String, Object> map = qrManager.decodeQrToken(QrType.STUDENT_FEE, qrToken);

        Boolean isApproved = (Boolean) map.get("feeStatus");

        return QrConverter.toStudentFeeResponseDTO(isApproved);
    }
}
