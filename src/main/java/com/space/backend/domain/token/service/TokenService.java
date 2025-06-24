package com.space.backend.domain.token.service;

import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.error.TokenErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.token.dto.TokenDto;
import com.space.backend.domain.token.ifs.TokenHelperIfs;
import com.space.backend.repository.redis.RedisDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenHelperIfs tokenHelperIfs;
    private final RedisDao redisDao;

    public TokenDto issueAccessToken(Long userId){
        var data = new HashMap<String, Object>();
        data.put("userId",userId);
        return tokenHelperIfs.issueAccessToken(data);
    }
    public TokenDto issueRefreshToken(Long userId){
        var data = new HashMap<String, Object>();
        data.put("userId",userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }
    public Long validationToken(String token){
        var map = tokenHelperIfs.validationTokenWithThrow(token);
        var userId = map.get("userId");
        Objects.requireNonNull(userId, ()->{throw new ApiException(ErrorCode.NULL_POINT);});
        return Long.parseLong(userId.toString());
    }
    public void setValues(Long userId, String refreshToken,long timeSecond){
        String key = "RT:" + userId;
        redisDao.setValues(key, refreshToken, timeSecond);
    }
    public Long getUserIdFromToken(String refreshToken){
        var map = tokenHelperIfs.validationTokenWithThrow(refreshToken);
        var userId = map.get("userId");
        Objects.requireNonNull(userId,()->{
            throw new ApiException(ErrorCode.NULL_POINT);
        });
        return Long.parseLong(userId.toString());

    }
    public String getRefreshTokenFromRedis(Long userId) {
        return Optional.ofNullable(redisDao.getValues("RT:" + userId))
                .orElseThrow(() -> new ApiException(TokenErrorCode.TOKEN_NOT_FOUND, "리프레시 토큰이 Redis에 존재하지 않습니다."));
    }
    public void logout(Long userId){
        String key = "RT:"+userId;
        Boolean deleted = redisDao.deleteValues(key);
        if(Boolean.FALSE.equals(deleted)){
            throw new ApiException(TokenErrorCode.TOKEN_NOT_FOUND, "리프레시 토큰이 Redis에 존재하지 않습니다.");
        }
    }
}
