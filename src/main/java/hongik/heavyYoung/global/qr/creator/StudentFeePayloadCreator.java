package hongik.heavyYoung.global.qr.creator;

import com.fasterxml.jackson.databind.ObjectMapper;
import hongik.heavyYoung.global.qr.QrType;
import hongik.heavyYoung.global.qr.payload.QrPayload;
import hongik.heavyYoung.global.qr.payload.StudentFeePayload;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StudentFeePayloadCreator implements QrPayloadCreator {
    private final ObjectMapper objectMapper;

    @Override
    public QrType getType() {
        return QrType.STUDENT_FEE;
    }

    @Override
    public QrPayload create(Map<String, Object> context) {
        return StudentFeePayload.builder()
                .qrType(QrType.STUDENT_FEE)
                .memberId((Long) context.get("memberId"))
                .studentId((String) context.get("studentId"))
                .feePaid((Boolean) context.get("feePaid"))
                .build();
    }

    @Override
    public QrPayload createFromClaims(Claims claims) {
        return objectMapper.convertValue(claims, StudentFeePayload.class);
    }
}
