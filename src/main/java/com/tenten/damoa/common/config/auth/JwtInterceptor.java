package com.tenten.damoa.common.config.auth;

import static com.tenten.damoa.common.exception.ErrorCode.*;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.member.domain.MemberRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        String bearerToken = request.getHeader("Authorization");

        if (handler instanceof HandlerMethod handlerMethod) {
            Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
            if (auth != null) {
                if (bearerToken == null) {
                    throw new BusinessException(LOGIN_UNAUTHORIZED);
                }

                String token = bearerToken.substring(7);
                if (!jwtUtil.verifyToken(token)) {
                    throw new BusinessException(INVALID_TOKEN_UNAUTHORIZED);
                }

                MemberRole role = MemberRole.valueOf(jwtUtil.getMemberRole(token));
                if (!role.equals(auth.role())) {
                    throw new BusinessException(PRE_MEMBER_FORBIDDEN);
                }

                request.setAttribute("account", jwtUtil.getMemberAccount(token));
            }
        }
        return true;
    }
}
