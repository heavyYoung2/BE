package hongik.heavyYoung.global.exception;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.apiPayload.dto.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ReasonDTO getErrorReason() {
        return code.getErrorReasonDTO();
    }
}
