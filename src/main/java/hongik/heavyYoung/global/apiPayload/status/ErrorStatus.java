package hongik.heavyYoung.global.apiPayload.status;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.apiPayload.dto.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // SERVER 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_001", "서버 에러, 관리자에게 문의 바랍니다."),

    // COMMON 에러
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON_001", "잘못된 요청 파라미터입니다."),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "COMMON_002", "시작일은 종료일보다 이후일 수 없습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "COMMON_003", "요청 값이 유효하지 않습니다."),

    // EVENT 에러
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "EVENT_001", "존재하지 않는 공지사항입니다."),
    EVENT_INVALID_DATE_RANGE(HttpStatus.NOT_FOUND, "EVENT_002", "시작일은 종료일보다 이후일 수 없습니다."),
    EVENT_INVALID_DATE_COMBINATION(HttpStatus.NOT_FOUND, "EVENT_003", "시작일과 종료일은 모두 입력하거나 모두 비워야 합니다."),

    // USER 에러
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
