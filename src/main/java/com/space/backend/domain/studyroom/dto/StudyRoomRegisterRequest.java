package com.space.backend.domain.studyroom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyRoomRegisterRequest {
    @NotNull
    private Long studyGroupId;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Integer maxCapacity;

    @NotNull
    private Integer pricePerHour;

}
