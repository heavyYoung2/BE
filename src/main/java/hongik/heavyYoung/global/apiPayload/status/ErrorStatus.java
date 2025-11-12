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
    EVENT_INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "EVENT_002", "시작일은 종료일보다 이후일 수 없습니다."),
    EVENT_INVALID_DATE_COMBINATION(HttpStatus.BAD_REQUEST, "EVENT_003", "시작일과 종료일은 모두 입력하거나 모두 비워야 합니다."),

    // USER 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "해당 유저가 존재하지 않습니다."),

    // Member 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_001", "해당 멤버가 존재하지 않습니다."),
    MEMBER_IS_BLACKLIST(HttpStatus.BAD_REQUEST, "MEMBER_002", "블랙리스트 멤버 입니다."),
    MEMBER_NOT_PAID(HttpStatus.BAD_REQUEST, "MEMBER_003", "학생회비를 납부하지 않았습니다."),
    MEMBER_HAS_OVERDUE_ITEM(HttpStatus.BAD_REQUEST, "MEMBER_004", "연체된 물품이 존재합니다."),
    MEMBER_ALREADY_RENTED_SAME_CATEGORY(HttpStatus.BAD_REQUEST, "MEMBER_005", "해당 종류의 물품을 이미 대여했습니다."),

    // Auth 에러
    MEMBER_ALREADY_EXIST(HttpStatus.FORBIDDEN, "AUTH_001", "해당 이메일의 회원이 이미 존재합니다"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTH_002", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "AUTH_003", "학교 이메일 형식이 아닙니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "AUTH_004", "비밀번호가 올바르지 않습니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "AUTH_005", "잘못된 JWT 서명입니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH_006", "JWT 토큰이 만료되었습니다."),
    INVALID_JWT_FORMAT(HttpStatus.UNAUTHORIZED, "AUTH_007", "JWT 형식이 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_008", "접근 권한이 없습니다."),

    // QrToken 에러
    QR_AUTH_INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "QR_001", "QR 토큰 서명이 유효하지 않습니다."),
    QR_AUTH_EXPIRED(HttpStatus.UNAUTHORIZED, "QR_002", "QR 토큰이 만료되었습니다."),
    QR_AUTH_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "QR_003", "QR 타입이 일치하지 않습니다."),
    QR_AUTH_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "QR_004", "존재하지 않는 QR 타입입니다."),
    QR_AUTH_INVALID_FORMAT(HttpStatus.UNAUTHORIZED, "QR_005", "토큰 형식이 올바르지 않습니다."),

    // Item 에러
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM_001", "해당 물건이 존재하지 않습니다."),
    ITEM_DELETE_NOT_ALLOWED_WHEN_RENTED(HttpStatus.CONFLICT, "ITEM_002", "대여중인 아이템은 삭제할 수 없습니다."),
    ITEM_QUANTITY_NON_POSITIVE(HttpStatus.BAD_REQUEST, "ITEM_003", "수량이 0 이하입니다."),

    // ItemCategory 에러
    ITEM_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM_CATEGORY_001", "해당 카테고리가 존재하지 않습니다."),

    // ItemRentalHistory 에러
    ITEM_RENTAL_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM_RENTAL_HISTORY_001", "해당 대여 기록이 존재하지 않습니다."),
    ALREADY_RETURN(HttpStatus.BAD_REQUEST, "ITEM_RENTAL_HISTORY_002", "이미 반납된 내역입니다.");

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
