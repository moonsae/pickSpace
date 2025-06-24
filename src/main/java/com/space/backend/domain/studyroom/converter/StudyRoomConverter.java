package com.space.backend.domain.studyroom.converter;

import com.space.backend.common.annotation.Converter;
import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.spacegroup.dto.SpaceGroupRegisterRequest;
import com.space.backend.domain.spacegroup.dto.SpaceGroupResponse;
import com.space.backend.domain.studyroom.dto.StudyRoomRegisterRequest;
import com.space.backend.domain.studyroom.dto.StudyRoomResponse;
import com.space.backend.entity.SpaceGroupEntity;
import com.space.backend.entity.StudyRoomEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class StudyRoomConverter {
    public StudyRoomEntity toEntity(StudyRoomRegisterRequest request){
        return Optional.ofNullable(request)
                .map(it->{
                    return StudyRoomEntity.builder()
                            .name(request.getName())
                            .description(request.getDescription())
                            .maxCapacity(request.getMaxCapacity())
                            .pricePerHour(request.getPricePerHour())
                            .build();
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"StudyRoomRegisterRequest NULL"));

    }
    public StudyRoomResponse toResponse(StudyRoomEntity studyRoomEntity){
        return Optional.ofNullable(studyRoomEntity)
                .map(it->{
                    return StudyRoomResponse.builder()
                            .id(studyRoomEntity.getId())
                            .studyGroupId(studyRoomEntity.getSpaceGroup().getId())
                            .name(studyRoomEntity.getName())
                            .description(studyRoomEntity.getDescription())
                            .maxCapacity(studyRoomEntity.getMaxCapacity())
                            .pricePerHour(studyRoomEntity.getPricePerHour())
                            .status(studyRoomEntity.getStatus())
                            .avgRating(studyRoomEntity.getAvgRating())
                            .reviewCount(studyRoomEntity.getReviewCount())
                            .registeredAt(studyRoomEntity.getRegisteredAt())
                            .build()
                            ;
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }
}

