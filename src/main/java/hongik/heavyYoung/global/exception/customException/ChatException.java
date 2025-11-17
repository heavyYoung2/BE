package hongik.heavyYoung.global.exception.customException;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.exception.GeneralException;

public class ChatException extends GeneralException {
    public ChatException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
