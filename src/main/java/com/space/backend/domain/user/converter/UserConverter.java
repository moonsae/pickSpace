package com.space.backend.domain.user.converter;

import com.space.backend.common.annotation.Converter;
import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.user.dto.UserRegisterRequest;
import com.space.backend.domain.user.dto.UserResponse;
import com.space.backend.entity.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
@Converter
@RequiredArgsConstructor
public class UserConverter {
    public UserEntity toEntity(UserRegisterRequest request){
        return Optional.ofNullable(request)
                .map(it->{
                    return UserEntity.builder()
                            .name(request.getName())
                            .email(request.getEmail())
                            .password(request.getPassword())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT,"UserRegisterRequest Null"));
    }
    public UserResponse toResponse(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it->{
                    return UserResponse.builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .email(userEntity.getEmail())
                            .password(userEntity.getPassword())
                            .role(userEntity.getRole())
                            .registeredAt(userEntity.getRegisteredAt())
                            .lastLoginAt(userEntity.getLastLoginAt())
                            .build()
                            ;
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"UserEntity Null"));
    }
}
