package hongik.heavyYoung.global.qr.payload;

import hongik.heavyYoung.global.qr.QrType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ReturnPayload implements QrPayload {

    private QrType qrType;
    private Long memberId;
    private String studentId;
    private String studentName;
    private Long rentalHistoryId;

    @Override
    public Map<String, Object> toMap() {
        return Map.of(
                "qrType", qrType,
                "memberId", memberId,
                "studentId", studentId,
                "studentName", studentName,
                "rentalHistoryId", rentalHistoryId
        );
    }
}
