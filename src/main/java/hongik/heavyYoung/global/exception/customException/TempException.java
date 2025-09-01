package hongik.heavyYoung.global.exception.customException;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.exception.GeneralException;

public class TempException extends GeneralException {
    public TempException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
