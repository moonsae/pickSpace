package com.space.backend.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RoomsStatus {
    PENDING("대기 중"),
    APPROVED("승인됨"),
    REJECTED("거절됨"),
    DISABLED("운영 중단")
    ;
    private String description;
}
