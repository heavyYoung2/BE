package hongik.heavyYoung.global.qr.payload;

import hongik.heavyYoung.global.qr.QrType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class StudentFeeQrPayload implements QrPayload {

    private final QrType qrType;
    private final Long memberId;
    private final String studentId;
    private final boolean feeStatus;


    @Override
    public Map<String, Object> toMap() {
        return Map.of(
                "qrType", qrType,
                "memberId", memberId,
                "studentId", studentId,
                "feeStatus", feeStatus
        );
    }

}
