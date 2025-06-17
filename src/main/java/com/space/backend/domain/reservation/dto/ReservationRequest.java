package com.space.backend.domain.reservation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {
    @NotNull
    private Long studyRoomId;

    @NotNull
    private LocalDate reservationDate;

    @Min(0) @Max(23)
    private Integer startSlot;

    @Min(1) @Max(24)
    private Integer endSlot;
}
