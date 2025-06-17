package com.space.backend.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReservationStatus {
    RESERVED("예약 완료"),
    CANCELED("취소"),
    COMPLETED("사용 완료")
    ;
    private String description;
}
