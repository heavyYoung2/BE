package hongik.heavyYoung.global.exception.customException;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.exception.GeneralException;

public class ItemCategoryException extends GeneralException {
    public ItemCategoryException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
