package com.space.backend.domain.studyroom.dto;

import com.space.backend.entity.enums.RoomsStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyRoomResponse {
    private Long id;
    private Long studyGroupId;
    private String name;
    private String description;
    private Integer maxCapacity;
    private Integer pricePerHour;
    private RoomsStatus status;
    private BigDecimal avgRating;
    private Integer reviewCount;
    private LocalDateTime registeredAt;
}
