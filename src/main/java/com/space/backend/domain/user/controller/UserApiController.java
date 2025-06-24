package com.space.backend.domain.user.controller;


import com.space.backend.common.api.Api;
import com.space.backend.domain.token.business.TokenBusiness;
import com.space.backend.domain.token.dto.TokenResponse;
import com.space.backend.domain.user.business.UserBusiness;
import com.space.backend.domain.user.dto.UserLoginRequest;
import com.space.backend.domain.user.dto.UserRegisterRequest;
import com.space.backend.domain.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserApiController {
    private final UserBusiness userBusiness;
    private final TokenBusiness tokenBusiness;

    //고객 가입
    @PostMapping("/register")
    public Api<UserResponse> register(
            @Valid
            @RequestBody Api<UserRegisterRequest> request
    ){
        var response = userBusiness.userRegister(request.getBody());
        return Api.OK(response);
    }

    //호스트 가입
    @PostMapping("/register-host")
    public Api<UserResponse> registerHost(
            @Valid
            @RequestBody Api<UserRegisterRequest> request
    ){
        var response = userBusiness.hostRegister(request.getBody());
        return Api.OK(response);
    }

    //관리자 가입
    @PostMapping("/register-admin")
    public Api<UserResponse> registerAdmin(
            @Valid
            @RequestBody Api<UserRegisterRequest> request
    ){
        var response = userBusiness.adminRegister(request.getBody());
        return Api.OK(response);
    }


    @PostMapping("/logout")
    public Api<String> logout(
            ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        tokenBusiness.logout(userId);
        return Api.OK("logout success");
    }

}
