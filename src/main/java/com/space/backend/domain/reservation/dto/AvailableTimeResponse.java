package com.space.backend.domain.reservation.dto;

import com.space.backend.entity.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableTimeResponse {
    private Long studyRoomId;
    private LocalDate date;
    private List<Integer> availableTimeSlots;
}
