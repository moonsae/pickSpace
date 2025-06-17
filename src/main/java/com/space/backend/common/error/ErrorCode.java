package com.space.backend.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum
ErrorCode implements ErrorCodeIfs{
    OK(200,200,"성공"),
    BAD_REQUEST(400,400,"잘못된 요청"),
    SERVER_ERROR(500, 500, "서버 에러"),
    NULL_POINT(512, 512,"Null Point");
    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
