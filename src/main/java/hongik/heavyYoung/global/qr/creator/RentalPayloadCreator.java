package hongik.heavyYoung.global.qr.creator;

import com.fasterxml.jackson.databind.ObjectMapper;
import hongik.heavyYoung.global.qr.QrType;
import hongik.heavyYoung.global.qr.payload.QrPayload;
import hongik.heavyYoung.global.qr.payload.RentalPayload;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RentalPayloadCreator implements QrPayloadCreator {
    private final ObjectMapper objectMapper;

    @Override
    public QrType getType() {
        return QrType.RENTAL;
    }

    @Override
    public QrPayload create(Map<String, Object> context) {
        return RentalPayload.builder()
                .qrType(QrType.RENTAL)
                .memberId((Long) context.get("memberId"))
                .studentId((String) context.get("studentId"))
                .studentName((String) context.get("studentName"))
                .feePaid((Boolean) context.get("feePaid"))
                .blacklisted((Boolean) context.get("blacklisted"))
                .itemCategoryId((Long) context.get("itemCategoryId"))
                .build();
    }

    @Override
    public QrPayload createFromClaims(Claims claims) {
        return objectMapper.convertValue(claims, RentalPayload.class);
    }
}
