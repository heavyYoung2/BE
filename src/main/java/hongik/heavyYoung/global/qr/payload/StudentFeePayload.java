package hongik.heavyYoung.global.qr.payload;

import hongik.heavyYoung.global.qr.QrType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class StudentFeePayload implements QrPayload {

    private QrType qrType;
    private Long memberId;
    private String studentId;
    private boolean feePaid;


    @Override
    public Map<String, Object> toMap() {
        return Map.of(
                "qrType", qrType,
                "memberId", memberId,
                "studentId", studentId,
                "feePaid", feePaid
        );
    }
}
