package hongik.heavyYoung.global.exception.customException;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.exception.GeneralException;

public class MemberException extends GeneralException {
    public MemberException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
