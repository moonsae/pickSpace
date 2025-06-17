package com.space.backend.domain.studyroom.service;

import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.error.RoomErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.studyroom.dto.StudyRoomResponse;
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

    public StudyRoomEntity register(StudyRoomEntity studyRoomEntity){
        return Optional.ofNullable(studyRoomEntity)
                .map(it-> {
                    it.setRegisteredAt(LocalDateTime.now());
                    it.setStatus(RoomsStatus.PENDING);
                    it.setAvgRating(BigDecimal.valueOf(0.00));
                    it.setReviewCount(0);
                    return studyRoomRepository.save(it);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }


    public List<StudyRoomEntity> findAllPending() {
        var list =  studyRoomRepository.findAllByStatusOrderByRegisteredAtDesc(RoomsStatus.PENDING);
        return list;
    }

    public StudyRoomEntity updateStatus(Long roomId, RoomsStatus status) {
        return studyRoomRepository.findById(roomId)
                .map(room->{
                    room.setStatus(status);
                    return studyRoomRepository.save(room);
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }

    public List<StudyRoomEntity> findAllApproved() {
        var list = studyRoomRepository.findAllByStatusOrderByRegisteredAtDesc(RoomsStatus.APPROVED);
        return list;
    }

    public List<StudyRoomEntity> findBySpaceGroup(Long spaceId) {
        var list = studyRoomRepository.findAllByStudyGroupIdAndStatusOrderByRegisteredAtDesc(spaceId,RoomsStatus.APPROVED);
        return list;
    }
}
