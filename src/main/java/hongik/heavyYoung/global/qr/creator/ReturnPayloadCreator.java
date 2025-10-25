package hongik.heavyYoung.global.qr.creator;

import com.fasterxml.jackson.databind.ObjectMapper;
import hongik.heavyYoung.global.qr.QrType;
import hongik.heavyYoung.global.qr.payload.QrPayload;
import hongik.heavyYoung.global.qr.payload.ReturnPayload;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReturnPayloadCreator implements QrPayloadCreator{
    private final ObjectMapper objectMapper;

    @Override
    public QrType getType() {
        return QrType.RETURN_ITEM;
    }

    @Override
    public QrPayload create(Map<String, Object> context) {
        return ReturnPayload.builder()
                .qrType(QrType.RETURN_ITEM)
                .studentId((String) context.get("studentId"))
                .studentName((String) context.get("studentName"))
                .rentalHistoryId((Long) context.get("rentalHistoryId"))
                .build();
    }
    @Override
    public QrPayload createFromClaims(Claims claims) {
        return objectMapper.convertValue(claims, ReturnPayload.class);
    }
}
