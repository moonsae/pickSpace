package com.space.backend.domain.user.controller;


import com.space.backend.common.api.Api;
import com.space.backend.domain.token.dto.TokenResponse;
import com.space.backend.domain.user.business.UserBusiness;
import com.space.backend.domain.user.dto.UserLoginRequest;
import com.space.backend.domain.user.dto.UserRegisterRequest;
import com.space.backend.domain.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminApiController {
    private final UserBusiness userBusiness;

    //관리자 가입
    @PostMapping("/register-admin")
    public Api<UserResponse> registerAdmin(
            @Valid
            @RequestBody Api<UserRegisterRequest> request
    ){
        var response = userBusiness.adminRegister(request.getBody());
        return Api.OK(response);
    }

}
