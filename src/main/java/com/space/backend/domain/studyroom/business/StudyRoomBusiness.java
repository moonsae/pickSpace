package com.space.backend.domain.studyroom.business;

import com.space.backend.common.annotation.Business;
import com.space.backend.domain.spacegroup.converter.SpaceGroupConverter;
import com.space.backend.domain.spacegroup.dto.SpaceGroupRegisterRequest;
import com.space.backend.domain.spacegroup.dto.SpaceGroupResponse;
import com.space.backend.domain.spacegroup.service.SpaceGroupService;
import com.space.backend.domain.studyroom.converter.StudyRoomConverter;
import com.space.backend.domain.studyroom.dto.StudyRoomRegisterRequest;
import com.space.backend.domain.studyroom.dto.StudyRoomResponse;
import com.space.backend.domain.studyroom.dto.StudyRoomStatusRequest;
import com.space.backend.domain.studyroom.service.StudyRoomService;
import com.space.backend.elastic.StudyRoomDocument;
import com.space.backend.elastic.StudyRoomSearchService;
import com.space.backend.entity.SpaceGroupEntity;
import com.space.backend.entity.StudyRoomEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StudyRoomBusiness {
    private final StudyRoomService studyRoomService;
    private final StudyRoomConverter studyRoomConverter;
    private final StudyRoomSearchService studyRoomSearchService;

    public StudyRoomResponse register(Long studyGroupId,StudyRoomRegisterRequest request){
        var entity = studyRoomConverter.toEntity(request);
        var newEntity = studyRoomService.register(studyGroupId,entity);
        var response = studyRoomConverter.toResponse(newEntity);
        return response;

    }

    public List<StudyRoomResponse> getAllPending() {
        var list = studyRoomService.findAllPending();
        return list.stream()
                .map(studyRoomConverter::toResponse)
                .collect(Collectors.toList());

    }

    public StudyRoomResponse updateStatus(Long roomId,StudyRoomStatusRequest req) {
        var status = req.getStatus();
        var newEntity = studyRoomService.updateStatus(roomId, status);
        var response = studyRoomConverter.toResponse(newEntity);
        return response;

    }

    public List<StudyRoomResponse> getAllApproved() {
        var list = studyRoomService.findAllApproved();
        return list.stream()
                .map(studyRoomConverter::toResponse)
                .collect(Collectors.toList());
    }

    public List<StudyRoomResponse> findBySpaceGroup(Long spaceId) {
        var list = studyRoomService.findBySpaceGroup(spaceId);
        return list.stream()
                .map(studyRoomConverter::toResponse)
                .collect(Collectors.toList());
    }
    public List<StudyRoomDocument> search(String keyword){
        var list = studyRoomSearchService.searchStudyRoom(keyword);
        return list;
    }
    public List<StudyRoomEntity> searchWithJoin(String keyword) {
        return studyRoomService.searchWithJoin(
                keyword
        );
    }
}
