package hongik.heavyYoung.global.exception;

import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.apiPayload.dto.ReasonDTO;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    // 비즈니스 예외 로그 템플릿
    private static final String BUSINESS_EX_LOG = """
            Business Exception
            ---------------------------
            Method      : {}
            URI         : {}
            QueryString : {}
            Code        : {}
            Message     : {}
            ---------------------------
            """;

    // 시스템 예외 로그 템플릿
    private static final String SYSTEM_EX_LOG = """
            System Exception
            ---------------------------
            Method      : {}
            URI         : {}
            QueryString : {}
            Code        : {}
            Message     : {}
            ---------------------------
            """;

    // GeneralException 발생 시 실행되는 예외 처리 메서드
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> onThrowException(
            GeneralException ex,
            HttpServletRequest request
    ) {
        ReasonDTO reasonDTO = ex.getErrorReason();

        // 예외 발생 로그 출력 (HTTP 메서드, URI, 쿼리스트링, 커스텀 에러 코드, 메시지, 스택트레이스)
        log.error(
                BUSINESS_EX_LOG,
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                reasonDTO.getCode(),
                reasonDTO.getMessage(),
                ex
        );

        // API 공통 응답 객체 생성 (실패 응답)
        ApiResponse<Object> body = ApiResponse.onFailure(
                reasonDTO.getCode(),
                reasonDTO.getMessage(),
                null
        );

        // ResponseEntity 형태로 클라이언트에 응답 반환 (Spring 표준 예외 처리 응답 구조)
        return super.handleExceptionInternal(
                ex,
                body,
                null,
                reasonDTO.getHttpStatus(),
                new ServletWebRequest(request)
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(
            Exception ex,
            WebRequest request
    ) {
        // HTTP 요청이 존재한다면, 상세 HTTP 정보 추출 후 로깅
        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
            log.error(
                    SYSTEM_EX_LOG,
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    httpServletRequest.getQueryString(),
                    ErrorStatus.INTERNAL_SERVER_ERROR.getCode(),
                    ex.getMessage(),
                    ex
            );
        }
        // HTTP 정보가 없는 경우
        else {
            log.error(
                    SYSTEM_EX_LOG,
                    "N/A",
                    "N/A",
                    "N/A",
                    ErrorStatus.INTERNAL_SERVER_ERROR.getCode(),
                    ex.getMessage(),
                    ex
            );
        }

        // API 공통 응답 객체 생성 (실패 응답)
        ApiResponse<Object> body = ApiResponse.onFailure(
                ErrorStatus.INTERNAL_SERVER_ERROR.getCode(),
                ErrorStatus.INTERNAL_SERVER_ERROR.getMessage(),
                null
        );

        // ResponseEntity 형태로 클라이언트에 응답 반환 (Spring 표준 예외 처리 응답 구조)
        return super.handleExceptionInternal(
                ex,
                body,
                null,
                ErrorStatus.INTERNAL_SERVER_ERROR.getHttpStatus(),
                request
        );    }

}
