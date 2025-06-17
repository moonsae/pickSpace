package com.space.backend.domain.reservation.dto;

import com.space.backend.entity.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {
    private Long reservationId;
    private Long studyRoomId;
    private String studyRoomName;
    private LocalDate reservationDate;
    private Integer startSlot;
    private Integer endSlot;
    private Integer totalPrice;
    private ReservationStatus status;
}
