package hongik.heavyYoung.global.apiPayload.status;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.apiPayload.dto.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_001", "서버 에러, 관리자에게 문의 바랍니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "해당 유저가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getErrorReasonDTO() {
        return ReasonDTO.builder()
                .httpStatus(httpStatus)
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }
}
