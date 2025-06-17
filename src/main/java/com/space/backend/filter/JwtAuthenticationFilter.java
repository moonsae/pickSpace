package com.space.backend.filter;

import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.error.TokenErrorCode;
import com.space.backend.common.error.UserErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.token.business.TokenBusiness;
import com.space.backend.entity.UserEntity;
import com.space.backend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenBusiness tokenBusiness;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("JWT Filter - URI: {}", request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.substring(7);

            try {
                Long userId = tokenBusiness.validationAccessToken(accessToken);
                //userId로 유저 조회
                UserEntity user = userRepository.findFirstById(userId)
                        .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

                // ⭐ 권한 설정 (ROLE_HOST 등)
                GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user.getId(), null, List.of(authority));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } catch (ApiException e) {
                log.warn("JWT 검증 실패: {}", e.getMessage());
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}