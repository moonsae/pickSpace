package com.space.backend.domain.spacegroup.business;

import com.space.backend.common.annotation.Business;
import com.space.backend.common.annotation.Converter;
import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.spacegroup.converter.SpaceGroupConverter;
import com.space.backend.domain.spacegroup.dto.SpaceGroupRegisterRequest;
import com.space.backend.domain.spacegroup.dto.SpaceGroupResponse;
import com.space.backend.domain.spacegroup.service.SpaceGroupService;
import com.space.backend.entity.SpaceGroupEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Business
public class SpaceGroupBusiness {
    private final SpaceGroupService spaceGroupService;
    private final SpaceGroupConverter spaceGroupConverter;

    public SpaceGroupResponse register(SpaceGroupRegisterRequest request,Long hostId){
        var entity = spaceGroupConverter.toEntity(request);
        var newEntity = spaceGroupService.register(entity,hostId);
        var response = spaceGroupConverter.toResponse(newEntity);
        return response;

    }
    public List<SpaceGroupResponse> getAllGroups() {
        List<SpaceGroupEntity> entityList = spaceGroupService.findAll();
        return entityList.stream()
                .map(spaceGroupConverter::toResponse)
                .toList();
    }

}
