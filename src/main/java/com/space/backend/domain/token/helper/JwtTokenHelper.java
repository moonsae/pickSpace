package com.space.backend.domain.token.helper;
import com.space.backend.common.error.TokenErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.repository.redis.RedisDao;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import com.space.backend.domain.token.dto.TokenDto;
import com.space.backend.domain.token.ifs.TokenHelperIfs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenHelper implements TokenHelperIfs {
    private final RedisDao redisDao;
    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;


    @Override
    public TokenDto issueAccessToken(Map<String, Object> data){
        return createToken(data,accessTokenPlusHour);
    }
    private TokenDto createToken(Map<String,Object> data, Long plusHour){
        var expiredLocalDataTime = LocalDateTime.now().plusHours(plusHour);
        var expiredAt = Date.from(expiredLocalDataTime.atZone(ZoneId.systemDefault()).toInstant());

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();
        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDataTime)
                .build();

    }


    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data){
        var refreshTokenDto = createToken(data,refreshTokenPlusHour);

        //Redis에 저장: "RT:{username}" → refresh token
        Long userId = (Long) data.get("userId");
        if (userId != null) {
            redisDao.setValues(
                    "RT:" + userId,
                    refreshTokenDto.getToken(),
                    refreshTokenPlusHour * 3600 // 초 단위 TTL
            );
        }
        return refreshTokenDto;
    }

   @Override
   public Map<String, Object> validationTokenWithThrow(String token) {
       var key = Keys.hmacShaKeyFor(secretKey.getBytes());

       try {
           var result = Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token);

           return new HashMap<>(result.getBody());

       } catch (ExpiredJwtException e) {
           throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
       } catch (JwtException e) {
           throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
       } catch (Exception e) {
           throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
       }
   }


}
