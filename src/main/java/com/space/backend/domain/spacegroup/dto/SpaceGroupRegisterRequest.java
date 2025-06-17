package com.space.backend.domain.spacegroup.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceGroupRegisterRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String description;

    @NotEmpty
    private Set<DayOfWeek> operatingDays;

    @Min(0) @Max(23)
    private Integer startSlot;

    @Min(1) @Max(24)
    private Integer endSlot;
}
