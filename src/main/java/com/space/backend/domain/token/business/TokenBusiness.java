package com.space.backend.domain.token.business;

import com.space.backend.common.annotation.Business;
import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.error.TokenErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.token.converter.TokenConverter;
import com.space.backend.domain.token.dto.TokenResponse;
import com.space.backend.domain.token.ifs.TokenHelperIfs;
import com.space.backend.domain.token.service.TokenService;
import com.space.backend.entity.UserEntity;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Business
public class TokenBusiness {
    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    public TokenResponse issueToken(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(UserEntity::getId)
                .map(userId ->{
                    var accessToken = tokenService.issueAccessToken(userId);
                    var refreshToken = tokenService.issueRefreshToken(userId);
                    // LocalDateTime → TTL(long 초 단위)로 변환
                    long ttlSeconds = Duration.between(LocalDateTime.now(), refreshToken.getExpiredAt()).getSeconds();

                    tokenService.setValues(
                            userEntity.getId(),
                            refreshToken.getToken(),
                            ttlSeconds
                    );
                    return tokenConverter.toResponse(accessToken,refreshToken);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }
    public Long validationAccessToken(String accessToken){
        var userId = tokenService.validationToken(accessToken);
        return userId;
    }

    public TokenResponse reissue(String refreshToken) {

        //1.리프레쉬 토큰 유효성 검증 및 userId 추출
        Long userId= tokenService.getUserIdFromToken(refreshToken);
        //2.레디스에 저장된 리프레시 토큰
        String redisToken = tokenService.getRefreshTokenFromRedis(userId);
        `
    }
}
