package hongik.heavyYoung.global.qr.payload;

import hongik.heavyYoung.global.qr.QrType;

import java.util.Map;

public interface QrPayload {
    Map<String, Object> toMap();
    QrType getQrType();
}
