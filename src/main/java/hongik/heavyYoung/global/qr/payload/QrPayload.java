package hongik.heavyYoung.global.qr.payload;

import hongik.heavyYoung.global.qr.QrType;

import java.util.Map;

/**
 * QR 토큰 페이로드의 공통 인터페이스. </br>
 * JWT 클레임 생성 및 QR 타입 식별을 위한 계약을 정의합니다.
 */
public interface QrPayload {

    /**
     * 페이로드를 JWT 클레임으로 변환합니다.
     * @return JWT 클레임으로 사용될 키-값 맵
     */
    Map<String, Object> toMap();

    /**
     * 이 페이로드의 QR 타입을 반환합니다.
     * @return QR 타입
     */
    QrType getQrType();
}
