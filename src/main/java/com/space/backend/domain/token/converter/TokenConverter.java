package com.space.backend.domain.token.converter;

import com.space.backend.common.annotation.Converter;
import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.token.dto.TokenDto;
import com.space.backend.domain.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Converter
public class TokenConverter {
    public TokenResponse toResponse(
            TokenDto accessToken,
            TokenDto refreshToken
    ){
        Objects.requireNonNull(accessToken,()->{throw new ApiException(ErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreshToken,()->{throw new ApiException(ErrorCode.NULL_POINT);});
        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                .build()
                ;
    }
}
