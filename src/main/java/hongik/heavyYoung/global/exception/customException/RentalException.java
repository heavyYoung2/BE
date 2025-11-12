package hongik.heavyYoung.global.exception.customException;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.exception.GeneralException;

public class RentalException extends GeneralException {
    public RentalException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
