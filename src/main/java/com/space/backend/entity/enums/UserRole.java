package com.space.backend.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {
    ROLE_ADMIN("관리자"),
    ROLE_USER("일반 유저"),
    ROLE_HOST("호스트")
    ;
    private String description;

}

