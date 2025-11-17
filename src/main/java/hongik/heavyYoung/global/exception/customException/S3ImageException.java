package hongik.heavyYoung.global.exception.customException;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.exception.GeneralException;

public class S3ImageException extends GeneralException {
    public S3ImageException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
