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

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/space")
public class SpaceGroupOpenApiController {
    private final SpaceGroupBusiness spaceGroupBusiness;
    // 업체 조회
    @GetMapping("/groups")
    public Api<List<SpaceGroupResponse>> getAllGroups(){
        List<SpaceGroupResponse> responseList = spaceGroupBusiness.getAllGroups();
        return Api.OK(responseList);
    }
}
