package com.space.backend.domain.token.business;

import com.space.backend.common.annotation.Business;
import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.error.TokenErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.token.converter.TokenConverter;
import com.space.backend.domain.token.dto.TokenResponse;
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
        if(!redisToken.equals(redisToken)){
            throw new ApiException(TokenErrorCode.INVALID_REFRESH_TOKEN,"리프레시 토큰이 일치하지 않습니다");
        }
        //3.새로운 access 토큰과 refresh 토큰 발급
        var newAccessToken = tokenService.issueAccessToken(userId);
        var newRefreshToken = tokenService.issueRefreshToken(userId);
        //4.레디스에 저장될 ttl 다시 설정
        long ttlSeconds = Duration.between(LocalDateTime.now(), newRefreshToken.getExpiredAt()).getSeconds();
        tokenService.setValues(userId, newRefreshToken.getToken(), ttlSeconds);

        // 5. 응답 객체로 변환 후 반환
        return tokenConverter.toResponse(newAccessToken, newRefreshToken);

    }
    public void logout(Long userId){
        tokenService.logout(userId);
    }
}
