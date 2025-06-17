package com.space.backend.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationErrorCode implements ErrorCodeIfs{
    RESERVATION_NOT_FOUND(404, 4001, "예약 정보를 찾을 수 없습니다."),
    ALREADY_CANCELED(400, 4002, "이미 취소된 예약입니다."),
    CANCEL_NOT_ALLOWED(400, 4003, "예약 하루 전까지만 취소가 가능합니다."),
    RESERVATION_NOT_ALLOWED(400, 4004, "예약 정보가 다릅니다.");
    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
