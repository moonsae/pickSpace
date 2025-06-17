package com.space.backend.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs{
    INVALID_TOKEN(400,2000,"유효하지 않은 토큰"),
    EXPIRED_TOKEN(400,2001,"만료된 토큰"),
    TOKEN_EXCEPTION(400,2002,"토큰 알 수 없는 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUND(400,2003,"인증 헤더 토큰 없음"),
    MISSING_REFRESH_TOKEN(400,2003,"Authorization 헤더가 없거나 형식이 잘못되었습니다."),
    INVALID_REFRESH_TOKEN(400,2004,"유효하지 않은 리프레쉬 토큰"),;
    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
