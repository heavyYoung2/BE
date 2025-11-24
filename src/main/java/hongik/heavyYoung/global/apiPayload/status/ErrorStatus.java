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

    // LOCKER 에러
    LOCKER_APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "LOCKER_001", "해당 사물함 신청이 존재하지 않습니다."),
    LOCKER_APPLICATION_NOT_ENDED(HttpStatus.NOT_FOUND, "LOCKER_002", "현재 진행중인 사물함 신청이 있습니다."),
    NO_AVAILABLE_LOCKER(HttpStatus.NOT_FOUND, "LOCKER_002", "신청 가능한 사물함이 없습니다."),
    NO_LOCKER_STRATEGY(HttpStatus.NOT_FOUND, "LOCKER_003", "사물함 신청 전략이 존재하지 않습니다."),
    ALREADY_APPLIED(HttpStatus.NOT_FOUND, "LOCKER_004", "사물함을 이미 신청하였습니다."),
    CAN_NOT_ASSIGN(HttpStatus.NOT_FOUND, "LOCKER_005", "배정할 수 없는 신청내역입니다."),
    LOCKER_APPLICATION_NOT_OPENED(HttpStatus.NOT_FOUND, "LOCKER_006", "현재 사물함 신청이 열려있지 않습니다."),
    LOCKER_NOT_FOUND(HttpStatus.NOT_FOUND, "LOCKER_007", "사물함이 존재하지 않습니다."),
    LOCKER_ASSIGNMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "LOCKER_008", "해당 사물함 배정이 존재하지 않습니다."),
    LOCKER_ALREADY_CANT_USE(HttpStatus.BAD_REQUEST, "LOCKER_009", "사물함이 이미 사용불가 상태입니다."),
    LOCKER_ALREADY_AVAILABLE(HttpStatus.BAD_REQUEST, "LOCKER_010", "사물함이 이미 사용가능 상태입니다."),
    LOCKER_SHOULD_BE_AVAILABLE(HttpStatus.BAD_REQUEST, "LOCKER_011", "사물함이 사용가능 상태일 때 수동 배정할 수 있습니다."),
    CURRENT_SEMESTER_NOT_FOUND(HttpStatus.NOT_FOUND, "LOCKER_012", "현재 학기가 존재하지 않습니다."),
    LOCKER_APPLICATION_INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "LOCKER_013", "사물함 신청 시작 일자는 마감 일자보다 나중이어야 합니다."),
    LOCKER_APPLICATION_SHOULD_BE_ENDED(HttpStatus.BAD_REQUEST, "LOCKER_014", "사물함 신청을 마감한 이후, 배정할 수 있습니다"),

    // USER 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "해당 유저가 존재하지 않습니다."),

    // Member 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_001", "해당 멤버가 존재하지 않습니다."),
    MEMBER_IS_BLACKLIST(HttpStatus.BAD_REQUEST, "MEMBER_002", "블랙리스트 멤버 입니다."),
    MEMBER_NOT_PAID(HttpStatus.BAD_REQUEST, "MEMBER_003", "학생회비를 납부하지 않았습니다."),
    MEMBER_HAS_OVERDUE_ITEM(HttpStatus.BAD_REQUEST, "MEMBER_004", "연체된 물품이 존재합니다."),
    MEMBER_ALREADY_RENTED_SAME_CATEGORY(HttpStatus.BAD_REQUEST, "MEMBER_005", "해당 종류의 물품을 이미 대여했습니다."),
    MEMBER_ALREADY_COUNCIL_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER_006", "이미 학생회 인원입니다."),
    MEMBER_NOT_STUDENT_COUNCIL(HttpStatus.BAD_REQUEST, "MEMBER_007", "학생회 인원이 아닙니다."),
    CANNOT_DELETE_OWNER(HttpStatus.BAD_REQUEST, "MEMBER_008", "최고 관리자는 삭제할 수 없습니다."),
    STUDENT_FEE_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_009", "학생회비가 존재하지 않습니다."),

    // Auth 에러
    MEMBER_ALREADY_EXIST(HttpStatus.FORBIDDEN, "AUTH_001", "해당 이메일의 회원이 이미 존재합니다"),
    PASSWORD_CONFIRM_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTH_002", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "AUTH_003", "학교 이메일 형식이 아닙니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "AUTH_004", "비밀번호가 올바르지 않습니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "AUTH_005", "잘못된 JWT 서명입니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH_006", "JWT 토큰이 만료되었습니다."),
    INVALID_JWT_FORMAT(HttpStatus.UNAUTHORIZED, "AUTH_007", "JWT 형식이 올바르지 않습니다."),
    AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_008", "접근 권한이 없습니다."),

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
    ALREADY_RETURN(HttpStatus.BAD_REQUEST, "ITEM_RENTAL_HISTORY_002", "이미 반납된 내역입니다."),

    // Rental 에러
    RENTAL_NOT_EQUAL(HttpStatus.BAD_REQUEST, "RENTAL_001", "대여 물품이 일치하지 않습니다."),
    RETURN_NOT_EQUAL(HttpStatus.BAD_REQUEST, "RENTAL_002", "반납 물품이 일치하지 않습니다."),

    // Email 에러
    EMAIL_NOT_SENT(HttpStatus.NOT_FOUND, "EMAIL_001", "이메일이 전송되지 않았습니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "EMAIL_002", "해당 이메일로 인증 코드가 전송되지 않았습니다."),
    EMAIL_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "EMAIL_003", "이메일과 코드가 일치하지 않습니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "EMAIL_004", "이메일 인증이 되지 않았습니다."),

    // S3 에러
    S3_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "S3_001", "이미지 업로드 중 오류가 발생했습니다."),
    S3_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "S3_002", "이미지 삭제 중 오류가 발생했습니다."),

    // 채팅 관련
    INVALID_API_KEY(HttpStatus.UNAUTHORIZED, "CHAT4001", "API 키가 잘못됐습니다."),
    QUOTA_EXCEEDED(HttpStatus.FORBIDDEN, "CHAT4002", "API 쿼터가 모두 소진되었습니다."),
    OPEN_AI_SERVER_ERROR(HttpStatus.BAD_GATEWAY, "CHAT4003", "OpenAI 서버에 연결할 수 없습니다. 잠시 후 다시 시도해주세요.");

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
