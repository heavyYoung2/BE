package hongik.heavyYoung.global.qr;

import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.QrException;
import hongik.heavyYoung.global.qr.creator.QrPayloadCreator;
import hongik.heavyYoung.global.qr.payload.QrPayload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class QrPayloadFactory {

    // QrType을 Key로, 해당 타입의 페이로드를 생성하는 Creator를 Value로 갖는 맵
    private final Map<QrType, QrPayloadCreator> creators;

    public QrPayloadFactory(List<QrPayloadCreator> creators) {
        this.creators = creators.stream()
                .collect(Collectors.toMap(QrPayloadCreator::getType, Function.identity()));
    }

    public QrPayload create(QrType qrType, Long memberId) {
        QrPayloadCreator creator = creators.get(qrType);
        if (creator == null) {
            throw new QrException(ErrorStatus.QR_AUTH_TYPE_MISMATCH);
        }
        return creator.create(memberId);
    }
}
