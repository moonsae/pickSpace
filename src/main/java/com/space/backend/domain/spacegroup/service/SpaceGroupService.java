package com.space.backend.domain.spacegroup.service;

import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.entity.SpaceGroupEntity;
import com.space.backend.repository.SpaceGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SpaceGroupService {
    private final SpaceGroupRepository spaceGroupRepository;

    public SpaceGroupEntity register(SpaceGroupEntity spaceGroupEntity,Long hostId){
        return Optional.ofNullable(spaceGroupEntity)
                .map(it-> {
                    spaceGroupEntity.setRegisteredAt(LocalDateTime.now());
                    spaceGroupEntity.setHostId(hostId);
                    return spaceGroupRepository.save(it);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"spaceGroupEntity null"));
    }
    @Transactional(readOnly = true)
    public List<SpaceGroupEntity> findAll() {
        List<SpaceGroupEntity> groups = spaceGroupRepository.findAll();

        // Lazy 필드 초기화
        groups.forEach(group -> group.getOperatingDays().size());
        return groups;
    }
}
