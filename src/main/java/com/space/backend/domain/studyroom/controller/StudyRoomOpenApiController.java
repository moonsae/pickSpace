package com.space.backend.domain.studyroom.controller;


import com.space.backend.common.api.Api;
import com.space.backend.domain.studyroom.business.StudyRoomBusiness;
import com.space.backend.domain.studyroom.dto.StudyRoomResponse;
import com.space.backend.domain.studyroom.dto.StudyRoomStatusRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/room")
public class StudyRoomOpenApiController {
    private final StudyRoomBusiness studyRoomBusiness;

    // 승인된 스터디룸 조회
    @GetMapping("/all")
    public Api<List<StudyRoomResponse>> getAllList(){
        var responseList = studyRoomBusiness.getAllApproved();
        return Api.OK(responseList);
    }

    // 업체 별 스터디룸 조회
    @GetMapping("/{spaceId}")
    public Api<List<StudyRoomResponse>> getBySpaceGroup(
            @PathVariable Long spaceId
    ){
        var responseList = studyRoomBusiness.findBySpaceGroup(spaceId);
        return Api.OK(responseList);
    }

}
