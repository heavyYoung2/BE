package hongik.heavyYoung.global.apiPayload.status;

import hongik.heavyYoung.global.apiPayload.code.BaseCode;
import hongik.heavyYoung.global.apiPayload.dto.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    OK(HttpStatus.OK, "COMMON_001", "성공입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getResponseDTO() {
        return ReasonDTO.builder()
                .httpStatus(httpStatus)
                .isSuccess(true)
                .code(code)
                .message(message)
                .build();
    }
}
