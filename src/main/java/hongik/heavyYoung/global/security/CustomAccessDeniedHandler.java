package hongik.heavyYoung.global.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_OWNER = "ROLE_OWNER";
    private static final Set<String> STUDENT_COUNCIL_ROLES = Set.of(ROLE_ADMIN, ROLE_OWNER);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        String path = request.getRequestURI();

        Collection<? extends GrantedAuthority> authorities = Optional
                .ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getAuthorities)
                .orElseGet(Collections::emptyList);

        String role = authorities.stream()
                .map(GrantedAuthority::getAuthority) // e.g. ROLE_USER, ROLE_ADMIN, ROLE_OWNER
                .findFirst()
                .orElse("ANONYMOUS");

        String message = "접근 권한이 없습니다. 로그인 후 이용해주세요."; // 기본 메시지
        int status = HttpServletResponse.SC_FORBIDDEN;

        // 학생회 전용 영역에 일반 학생(USER)이 접근한 경우
         if (ROLE_USER.equals(role) && (path.startsWith("/admin") || path.startsWith("/owner"))) {
            message = "학생회 권한이 필요합니다.";
        }

        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        // 프로젝트 공통 응답 포맷에 맞춰 작성
        String body = """
                {"isSuccess":false,"code":"AUTH_008","message":"%s","result":null}
                """.formatted(message);
        response.getWriter().write(body);
    }
}
