package hongik.heavyYoung.global.exception.customException;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.exception.GeneralException;

public class AuthException extends GeneralException {
    public AuthException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
