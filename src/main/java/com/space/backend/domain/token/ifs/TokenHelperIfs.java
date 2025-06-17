package com.space.backend.domain.token.ifs;

import com.space.backend.domain.token.dto.TokenDto;

import java.util.Map;

public interface TokenHelperIfs {
    TokenDto issueAccessToken(Map<String, Object> data);
    TokenDto issueRefreshToken(Map<String,Object> data);
    Map<String, Object> validationTokenWithThrow(String token);
}
