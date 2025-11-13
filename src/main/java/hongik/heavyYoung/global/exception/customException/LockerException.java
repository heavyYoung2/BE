package hongik.heavyYoung.global.exception.customException;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.exception.GeneralException;

public class LockerException extends GeneralException {
    public LockerException(BaseErrorCode baseErrorCode) { super(baseErrorCode); }
}
