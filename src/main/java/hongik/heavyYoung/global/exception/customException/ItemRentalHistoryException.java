package hongik.heavyYoung.global.exception.customException;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.exception.GeneralException;

public class ItemRentalHistoryException extends GeneralException {
    public ItemRentalHistoryException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
