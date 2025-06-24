package com.space.backend.domain.studyroom.service;

import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.error.RoomErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.studyroom.dto.StudyRoomResponse;
import com.space.backend.elastic.StudyRoomSearchService;
import com.space.backend.entity.SpaceGroupEntity;
import com.space.backend.entity.StudyRoomEntity;
import com.space.backend.entity.enums.RoomsStatus;
import com.space.backend.repository.SpaceGroupRepository;
import com.space.backend.repository.StudyRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudyRoomService {
    private final StudyRoomRepository studyRoomRepository;
    private final SpaceGroupRepository spaceGroupRepository;
    private final StudyRoomSearchService studyRoomSearchService;

    public StudyRoomEntity register(Long spaceGroupId,StudyRoomEntity studyRoomEntity){

        SpaceGroupEntity spaceGroup = spaceGroupRepository.findById(spaceGroupId)
                .orElseThrow(()->new ApiException(RoomErrorCode.NOT_FOUND_SPACE));
        return Optional.ofNullable(studyRoomEntity)
                .map(it-> {
                    it.setSpaceGroup(spaceGroup);
                    it.setRegisteredAt(LocalDateTime.now());
                    it.setStatus(RoomsStatus.PENDING);
                    it.setAvgRating(BigDecimal.valueOf(0.00));
                    it.setReviewCount(0);
                    StudyRoomEntity saved = studyRoomRepository.save(it);
                    if (saved.getStatus() == RoomsStatus.APPROVED) {
                        studyRoomSearchService.indexStudyRoom(saved);
                    }
                    return saved;
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }


    public List<StudyRoomEntity> findAllPending() {
        var list =  studyRoomRepository.findAllByStatusOrderByRegisteredAtDesc(RoomsStatus.PENDING);
        return list;
    }

    public StudyRoomEntity updateStatus(Long roomId, RoomsStatus status) {
        return studyRoomRepository.findById(roomId)
                .map(room -> {
                    room.setStatus(status);
                    StudyRoomEntity saved = studyRoomRepository.save(room);

                    //승인 상태로 바뀌었을 때 색인 등록
                    if (status == RoomsStatus.APPROVED) {
                        studyRoomSearchService.indexStudyRoom(saved);
                    }

                    return saved;
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public List<StudyRoomEntity> findAllApproved() {
        var list = studyRoomRepository.findAllByStatusOrderByRegisteredAtDesc(RoomsStatus.APPROVED);
        return list;
    }

    public List<StudyRoomEntity> findBySpaceGroup(Long spaceId) {
        var spaceGroup = spaceGroupRepository.findById(spaceId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "해당 업체가 존재하지 않습니다."));

        return studyRoomRepository.findAllBySpaceGroupAndStatusOrderByRegisteredAtDesc(spaceGroup, RoomsStatus.APPROVED);
    }
}
