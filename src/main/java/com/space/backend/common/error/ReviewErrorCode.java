package com.space.backend.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReviewErrorCode implements ErrorCodeIfs{
    REVIEW_ERROR_CODE(400, 2031, "존재 하지 않는 리뷰입니다"),
    ALREADY_REPLIED(400,2032,"이미 답글을 작성했습니다.");
    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
