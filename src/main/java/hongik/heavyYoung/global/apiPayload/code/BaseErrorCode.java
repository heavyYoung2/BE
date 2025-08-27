package hongik.heavyYoung.global.apiPayload.code;

import hongik.heavyYoung.global.apiPayload.dto.ReasonDTO;
import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
    ReasonDTO getErrorReasonDTO();
}
