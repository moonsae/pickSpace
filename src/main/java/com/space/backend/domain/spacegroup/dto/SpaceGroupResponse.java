package com.space.backend.domain.spacegroup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceGroupResponse {
    private Long id;
    private Long hostId;
    private String name;
    private String address;
    private String description;
    private LocalDateTime registeredAt;
    private Set<DayOfWeek> operatingDays;
    private Integer startSlot;
    private Integer endSlot;
}
