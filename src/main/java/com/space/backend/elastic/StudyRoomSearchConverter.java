/*
package com.space.backend.elastic;

import com.space.backend.common.annotation.Converter;
import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.studyroom.dto.StudyRoomRegisterRequest;
import com.space.backend.entity.StudyRoomEntity;
import com.space.backend.entity.enums.RoomsStatus;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class StudyRoomSearchConverter {
    public StudyRoomDocument toDocument(StudyRoomEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> {
                    return StudyRoomEntity.builder()
                            .id(entity.getId())
                            .name(entity.getName())
                            .description(entity.getDescription())
                            .address(entity.getStudyGroupId())
                            .status(entity)
                            .avgRating(entity)
                            .reviewCount(entity)
                            .pricePerHour(entity)
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "StudyRoomRegisterRequest NULL"));

    }
}
*/
