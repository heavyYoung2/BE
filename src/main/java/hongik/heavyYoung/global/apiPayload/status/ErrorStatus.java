package hongik.heavyYoung.global.apiPayload.status;

import hongik.heavyYoung.global.apiPayload.code.BaseErrorCode;
import hongik.heavyYoung.global.apiPayload.dto.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_001", "서버 에러, 관리자에게 문의 바랍니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON_001", "잘못된 요청 파라미터입니다."),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "COMMON_002", "시작일은 종료일보다 이후일 수 없습니다."),

    // Member 관련
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_001", "해당 멤버가 존재하지 않습니다."),

    // QrToken
    QR_AUTH_INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "QR_001", "QR 토큰 서명이 유효하지 않습니다."),
    QR_AUTH_EXPIRED(HttpStatus.UNAUTHORIZED, "QR_002", "QR 토큰이 만료되었습니다."),
    QR_AUTH_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "QR_003", "QR 타입이 일치하지 않습니다.");

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
