package com.space.backend.domain.spacegroup.controller;


import com.space.backend.common.api.Api;
import com.space.backend.domain.spacegroup.business.SpaceGroupBusiness;
import com.space.backend.domain.spacegroup.dto.SpaceGroupRegisterRequest;
import com.space.backend.domain.spacegroup.dto.SpaceGroupResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/host")
public class SpaceGroupHostApiController {
    private final SpaceGroupBusiness spaceGroupBusiness;
    //업체 등록
    @PostMapping("/groups/create")
    public Api<SpaceGroupResponse> registerGroup(
            @Valid
            @RequestBody Api<SpaceGroupRegisterRequest> request
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long hostId = (Long) authentication.getPrincipal();
        var response = spaceGroupBusiness.register(request.getBody(),hostId);
        return Api.OK(response);
    }
}
