package hongik.heavyYoung.global.qr.creator;

import hongik.heavyYoung.global.qr.QrType;
import hongik.heavyYoung.global.qr.payload.QrPayload;
import io.jsonwebtoken.Claims;

import java.util.Map;


/**
 * QR 페이로드 생성을 위한 팩토리 인터페이스.
 * 컨텍스트 맵 또는 JWT Claims로부터 특정 타입의 QrPayload를 생성합니다.
 */
public interface QrPayloadCreator {
    /**
     * 이 생성자가 처리하는 QR 타입을 반환합니다.
     * @return QR 타입
     */
    QrType getType();

    /**
     * 컨텍스트 맵으로부터 페이로드를 생성합니다.
     * @param context 페이로드 생성에 필요한 컨텍스트 정보
     * @return 생성된 QR 페이로드
     */
    QrPayload create(Map<String, Object> context);

    /**
     * JWT Claims로부터 페이로드를 복원합니다.
     * @param claims JWT 클레임 객체
     * @return 복원된 QR 페이로드
     */
    QrPayload createFromClaims(Claims claims);
}
