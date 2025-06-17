package com.space.backend.domain.spacegroup.converter;

import com.space.backend.common.annotation.Converter;
import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.spacegroup.dto.SpaceGroupRegisterRequest;
import com.space.backend.domain.spacegroup.dto.SpaceGroupResponse;
import com.space.backend.entity.SpaceGroupEntity;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class SpaceGroupConverter {
    public SpaceGroupEntity toEntity(SpaceGroupRegisterRequest request){
        return Optional.ofNullable(request)
                .map(it->{
                    return SpaceGroupEntity.builder()
                            .name(request.getName())
                            .address(request.getAddress())
                            .description(request.getDescription())
                            .operatingDays(request.getOperatingDays())
                            .startSlot(request.getStartSlot())
                            .endSlot(request.getEndSlot())
                            .build();
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"SpaceGroupRegisterRequest NULL"));

    }
    public SpaceGroupResponse toResponse(SpaceGroupEntity spaceGroupEntity){
        return Optional.ofNullable(spaceGroupEntity)
                .map(it->{
                    return SpaceGroupResponse.builder()
                            .id(spaceGroupEntity.getId())
                            .hostId(spaceGroupEntity.getHostId())
                            .name(spaceGroupEntity.getName())
                            .address(spaceGroupEntity.getAddress())
                            .description(spaceGroupEntity.getDescription())
                            .registeredAt(spaceGroupEntity.getRegisteredAt())
                            .operatingDays(new HashSet<>(spaceGroupEntity.getOperatingDays()))
                            .startSlot(spaceGroupEntity.getStartSlot())
                            .endSlot(spaceGroupEntity.getEndSlot())
                            .build();
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"SpaceGroupEntity NULL"));
    }
}
