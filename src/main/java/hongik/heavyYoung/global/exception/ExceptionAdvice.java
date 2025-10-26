package hongik.heavyYoung.global.exception;

import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.apiPayload.dto.ReasonDTO;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

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

    // 클라이언트 예외 로그 템플릿
    private static final String CLIENT_EX_LOG = """
            Client Exception
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

    // MethodArgumentTypeMismatchException(파라미터 형식 오류) 발생 시 실행되는 예외 처리 메서드
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request
    ) {
        // HTTP 요청이 존재한다면, 상세 HTTP 정보 추출 후 로깅
        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
            log.error(
                    CLIENT_EX_LOG,
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    httpServletRequest.getQueryString(),
                    ErrorStatus.INVALID_PARAMETER.getCode(),
                    ex.getMessage(),
                    ex
            );
        }
        // HTTP 정보가 없는 경우
        else {
            log.error(
                    CLIENT_EX_LOG,
                    "N/A",
                    "N/A",
                    "N/A",
                    ErrorStatus.INVALID_PARAMETER.getCode(),
                    ex.getMessage(),
                    ex
            );
        }

        // API 공통 응답 객체 생성 (실패 응답)
        ApiResponse<Object> body = ApiResponse.onFailure(
                ErrorStatus.INVALID_PARAMETER.getCode(),
                ErrorStatus.INVALID_PARAMETER.getMessage(),
                null
        );

        // ResponseEntity 형태로 클라이언트에 응답 반환 (Spring 표준 예외 처리 응답 구조)
        return super.handleExceptionInternal(
                ex,
                body,
                null,
                ErrorStatus.INVALID_PARAMETER.getHttpStatus(),
                request
        );
    }

    // MethodArgumentNotValidException(@Valid 검증 실패) 발생 시 실행되는 예외 처리 메서드
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        // 검증 실패한 모든 필드 에러 메시지 추출
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        // HTTP 요청이 존재한다면, 상세 HTTP 정보 추출 후 로깅
        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
            log.error(
                    CLIENT_EX_LOG,
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    httpServletRequest.getQueryString(),
                    ErrorStatus.VALIDATION_ERROR.getCode(),
                    errorMessages,
                    ex
            );
        } else {
            log.error(
                    CLIENT_EX_LOG,
                    "N/A",
                    "N/A",
                    "N/A",
                    ErrorStatus.VALIDATION_ERROR.getCode(),
                    errorMessages,
                    ex
            );
        }

        // API 공통 응답 객체 생성 (실패 응답)
        ApiResponse<Object> body = ApiResponse.onFailure(
                ErrorStatus.VALIDATION_ERROR.getCode(),
                ErrorStatus.VALIDATION_ERROR.getMessage(),
                errorMessages
        );

        // ResponseEntity 형태로 클라이언트에 응답 반환
        return super.handleExceptionInternal(
                ex,
                body,
                headers,
                ErrorStatus.VALIDATION_ERROR.getHttpStatus(),
                request
        );
    }

    // HttpMessageNotReadableException(RequestBody JSON 파싱 오류) 발생 시 실행되는 예외 처리 메서드
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        // HTTP 요청이 존재한다면, 상세 HTTP 정보 추출 후 로깅
        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
            log.error(
                    CLIENT_EX_LOG,
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    httpServletRequest.getQueryString(),
                    ErrorStatus.INVALID_PARAMETER.getCode(),
                    ex.getMessage(),
                    ex
            );
        }
        // HTTP 정보가 없는 경우
        else {
            log.error(
                    CLIENT_EX_LOG,
                    "N/A",
                    "N/A",
                    "N/A",
                    ErrorStatus.INVALID_PARAMETER.getCode(),
                    ex.getMessage(),
                    ex
            );
        }

        // API 공통 응답 객체 생성 (실패 응답)
        ApiResponse<Object> body = ApiResponse.onFailure(
                ErrorStatus.INVALID_PARAMETER.getCode(),
                ErrorStatus.INVALID_PARAMETER.getMessage(),
                null
        );

        // ResponseEntity 형태로 클라이언트에 응답 반환 (Spring 표준 예외 처리 응답 구조)
        return super.handleExceptionInternal(
                ex,
                body,
                headers,
                ErrorStatus.INVALID_PARAMETER.getHttpStatus(),
                request
        );
    }

    // ConstraintViolationException(@Validated 검증 실패, 파라미터 제약 위반) 발생 시 실행되는 예외 처리 메서드
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request
    ) {
        // 검증 실패한 모든 메시지 추출
        List<String> errorMessages = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        // HTTP 요청이 존재한다면, 상세 HTTP 정보 추출 후 로깅
        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
            log.error(
                    CLIENT_EX_LOG,
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    httpServletRequest.getQueryString(),
                    ErrorStatus.VALIDATION_ERROR.getCode(),
                    errorMessages,
                    ex
            );
        } else {
            log.error(
                    CLIENT_EX_LOG,
                    "N/A",
                    "N/A",
                    "N/A",
                    ErrorStatus.VALIDATION_ERROR.getCode(),
                    errorMessages,
                    ex
            );
        }

        // API 공통 응답 객체 생성 (실패 응답)
        ApiResponse<Object> body = ApiResponse.onFailure(
                ErrorStatus.VALIDATION_ERROR.getCode(),
                ErrorStatus.VALIDATION_ERROR.getMessage(),
                errorMessages
        );

        // ResponseEntity 형태로 클라이언트에 응답 반환
        return super.handleExceptionInternal(
                ex,
                body,
                null,
                ErrorStatus.VALIDATION_ERROR.getHttpStatus(),
                request
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
        );
    }

    // MissingServletRequestParameterException(RequestParam 누락) 발생 시 실행되는 예외 처리 메서드
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        // 누락된 파라미터 이름으로 메시지 생성
        List<String> errorMessages = List.of(ex.getParameterName() + " 파라미터는 필수입니다.");

        // HTTP 요청이 존재한다면, 상세 HTTP 정보 추출 후 로깅
        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
            log.error(
                    CLIENT_EX_LOG,
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    httpServletRequest.getQueryString(),
                    ErrorStatus.INVALID_PARAMETER.getCode(),
                    errorMessages,
                    ex
            );
        } else {
            log.error(
                    CLIENT_EX_LOG,
                    "N/A",
                    "N/A",
                    "N/A",
                    ErrorStatus.INVALID_PARAMETER.getCode(),
                    errorMessages,
                    ex
            );
        }

        // API 공통 응답 객체 생성 (실패 응답)
        ApiResponse<Object> body = ApiResponse.onFailure(
                ErrorStatus.INVALID_PARAMETER.getCode(),
                ErrorStatus.INVALID_PARAMETER.getMessage(),
                errorMessages
        );

        // ResponseEntity 형태로 클라이언트에 응답 반환
        return super.handleExceptionInternal(
                ex,
                body,
                headers,
                ErrorStatus.INVALID_PARAMETER.getHttpStatus(),
                request
        );
    }
}
