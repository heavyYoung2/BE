package hongik.heavyYoung.global.qr.creator;

import hongik.heavyYoung.global.qr.QrType;
import hongik.heavyYoung.global.qr.payload.QrPayload;
import io.jsonwebtoken.Claims;

import java.util.Map;


public interface QrPayloadCreator {
    QrType getType();
    QrPayload create(Map<String, Object> context);
    QrPayload createFromClaims(Claims claims);
}
