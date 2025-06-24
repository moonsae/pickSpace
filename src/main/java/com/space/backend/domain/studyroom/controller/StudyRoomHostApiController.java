package com.space.backend.domain.studyroom.controller;


import com.space.backend.common.api.Api;
import com.space.backend.domain.spacegroup.business.SpaceGroupBusiness;
import com.space.backend.domain.spacegroup.dto.SpaceGroupRegisterRequest;
import com.space.backend.domain.spacegroup.dto.SpaceGroupResponse;
import com.space.backend.domain.studyroom.business.StudyRoomBusiness;
import com.space.backend.domain.studyroom.dto.StudyRoomRegisterRequest;
import com.space.backend.domain.studyroom.dto.StudyRoomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/host")
public class StudyRoomHostApiController {
    private final StudyRoomBusiness studyRoomBusiness;
    //업체 등록
    @PostMapping("/rooms/create")
    public Api<StudyRoomResponse> registerRoom(
            @Valid
            @RequestBody Api<StudyRoomRegisterRequest> request
    ){
        var req = request.getBody();
        Long studyGroupId = req.getStudyGroupId();
        var response = studyRoomBusiness.register(studyGroupId,req);
        return Api.OK(response);
    }
}
