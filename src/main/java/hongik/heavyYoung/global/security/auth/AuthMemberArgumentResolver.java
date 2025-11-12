package hongik.heavyYoung.global.security.auth;

import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.AuthException;
import hongik.heavyYoung.global.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMemberId.class)
                && Long.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthException(ErrorStatus.AUTH_UNAUTHORIZED); // 401로 매핑되도록 설정
        }
        String token = authHeader.substring(7); // "Bearer " 이후

        // access 토큰 파싱 → subject = memberId
        Claims claims = jwtProvider.parseAuthClaims(token);
        String sub = claims.getSubject();
        if (sub == null) throw new AuthException(ErrorStatus.AUTH_UNAUTHORIZED);

        try {
            return Long.valueOf(sub);
        } catch (NumberFormatException ex) {
            throw new AuthException(ErrorStatus.INVALID_JWT_FORMAT);
        }
    }
}
