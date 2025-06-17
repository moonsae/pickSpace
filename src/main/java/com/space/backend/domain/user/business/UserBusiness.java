package com.space.backend.domain.user.business;

import com.space.backend.common.annotation.Business;
import com.space.backend.common.error.UserErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.token.business.TokenBusiness;
import com.space.backend.domain.token.dto.TokenResponse;
import com.space.backend.domain.user.converter.UserConverter;
import com.space.backend.domain.user.dto.UserLoginRequest;
import com.space.backend.domain.user.dto.UserRegisterRequest;
import com.space.backend.domain.user.dto.UserResponse;
import com.space.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Business
public class UserBusiness {
    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;
    public UserResponse userRegister(UserRegisterRequest request){
        var entity = userConverter.toEntity(request);
        var newEntity = userService.userRegister(entity);
        var response = userConverter.toResponse(newEntity);
        return response;
    }
    public UserResponse hostRegister(UserRegisterRequest request){
        var entity = userConverter.toEntity(request);
        var newEntity = userService.hostRegister(entity);
        var response = userConverter.toResponse(newEntity);
        return response;
    }
    public UserResponse adminRegister(UserRegisterRequest request){
        var entity = userConverter.toEntity(request);
        var newEntity = userService.adminRegister(entity);
        var response = userConverter.toResponse(newEntity);
        return response;
    }
    public TokenResponse login(UserLoginRequest request){
        var userEntity = userService.login(request.getEmail(),request.getPassword());
        Objects.requireNonNull(userEntity,()->{throw new ApiException(UserErrorCode.USER_NOT_FOUND);
        });
        var tokenResponse = tokenBusiness.issueToken(userEntity);
        return tokenResponse;


    }
    public UserResponse me(Long userId) {
        var userEntity = userService.getUserWithThrow(userId);
        var response = userConverter.toResponse(userEntity);
        return response;
    }
}
