package com.space.backend.domain.token.service;

import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.token.dto.TokenDto;
import com.space.backend.domain.token.ifs.TokenHelperIfs;
import com.space.backend.repository.redis.RedisDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

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
        Object userId = map.get("userId");
        if(userId==null) throw new ApiException(ErrorCode.NULL_POINT);
        return Long.parseLong(userId.toString());

    }
    public String getRefreshTokenFromRedis(Long userId){
        return redisDao.getValues("RT:"+userId);
    }

}
