package hongik.heavyYoung.global.exception.customException;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.exception.GeneralException;

public class ItemException extends GeneralException {
    public ItemException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
