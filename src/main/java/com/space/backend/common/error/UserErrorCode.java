package com.space.backend.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs{
    USER_NOT_FOUND(400, 1001, "존재하지 않는 사용자입니다."),
    INVALID_PASSWORD(400, 1002, "비밀번호가 일치하지 않습니다."),
    ACCESS_DENIED(403, 1003, "접근 권한이 없습니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
