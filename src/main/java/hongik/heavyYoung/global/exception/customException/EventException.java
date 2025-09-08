package hongik.heavyYoung.global.exception.customException;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.exception.GeneralException;

public class EventException extends GeneralException {
    public EventException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
