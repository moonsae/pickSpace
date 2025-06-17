package com.space.backend.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoomErrorCode implements ErrorCodeIfs{
    ROOM_ERROR_CODE(400, 2011, "존재 하지 않는 스터디룸입니다"),
    ALREADY_RESERVE(400, 2021, "이미 예약된 방입니다"),
    INVALID_TIME_SLOT(400,2022, "잘못된 시간 설정입니다."),
    INVALID_RESERVATION_DATE(400,2023, "예약은 최소 하루 전에만 가능합니다."),
    NOT_OPERATING_DAY(400,2024, "해당 요일은 영업하지 않습니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
