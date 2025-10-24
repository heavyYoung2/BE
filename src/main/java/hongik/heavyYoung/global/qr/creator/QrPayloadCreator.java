package hongik.heavyYoung.global.qr.creator;

import hongik.heavyYoung.global.qr.QrType;
import hongik.heavyYoung.global.qr.payload.QrPayload;
import io.jsonwebtoken.Claims;


public interface QrPayloadCreator {
    QrType getType();
    QrPayload create(Long memberId);
    QrPayload createFromClaims(Claims claims);
}
