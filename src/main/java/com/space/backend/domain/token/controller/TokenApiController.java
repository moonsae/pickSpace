package com.space.backend.domain.token.controller;


import com.space.backend.common.api.Api;
import com.space.backend.common.error.TokenErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.token.business.TokenBusiness;
import com.space.backend.domain.token.dto.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/token")
public class TokenApiController {
    private final TokenBusiness tokenBusiness;

    @PostMapping("/reissue")
    public Api<TokenResponse> reissue(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        // 1. Authorization 헤더 유효성 검사
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ApiException(TokenErrorCode.MISSING_REFRESH_TOKEN, "Authorization 헤더가 없거나 형식이 잘못되었습니다.");
        }
        // 2. Bearer 접두사 제거 ->실제 리프레시 토큰 추출
        String refreshToken = authHeader.substring(7);

        // 3. 재발급 처리
        TokenResponse response  = tokenBusiness.reissue(refreshToken);
        return Api.OK(response);
    }


}
