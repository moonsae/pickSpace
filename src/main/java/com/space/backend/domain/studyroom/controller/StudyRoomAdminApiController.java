package com.space.backend.domain.studyroom.controller;


import com.space.backend.common.api.Api;
import com.space.backend.domain.spacegroup.dto.SpaceGroupResponse;
import com.space.backend.domain.studyroom.business.StudyRoomBusiness;
import com.space.backend.domain.studyroom.dto.StudyRoomRegisterRequest;
import com.space.backend.domain.studyroom.dto.StudyRoomResponse;
import com.space.backend.domain.studyroom.dto.StudyRoomStatusRequest;
import com.space.backend.domain.user.business.UserBusiness;
import com.space.backend.domain.user.dto.UserRegisterRequest;
import com.space.backend.domain.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/rooms")
public class StudyRoomAdminApiController {
    private final StudyRoomBusiness studyRoomBusiness;

    // 승인 대기 상태의 스터디룸 조회
    @GetMapping("/pending-list")
    public Api<List<StudyRoomResponse>> pendingList(){
        var responseList = studyRoomBusiness.getAllPending();
        return Api.OK(responseList);
    }

    @PutMapping("/{roomId}/status")
    public Api<StudyRoomResponse> updateRoomStatus(
            @PathVariable Long roomId,
            @Valid
            @RequestBody Api<StudyRoomStatusRequest> request
    ){
        var req = request.getBody();
        var response = studyRoomBusiness.updateStatus(roomId,req);
        return Api.OK(response);
    }


}
