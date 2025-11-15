package hongik.heavyYoung.global.security.auth;

import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.AuthException;
import hongik.heavyYoung.global.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new AuthException(ErrorStatus.AUTH_UNAUTHORIZED);
        }

        // principal이 JwtAuthFilter에서 넣어둔 subject(memberId)
        String principal = (String) authentication.getPrincipal();

        try {
            return Long.valueOf(principal);
        } catch (NumberFormatException ex) {
            throw new AuthException(ErrorStatus.INVALID_JWT_FORMAT);
        }
    }
}
