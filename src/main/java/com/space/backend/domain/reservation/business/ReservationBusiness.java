package com.space.backend.domain.reservation.business;

import com.space.backend.common.annotation.Business;
import com.space.backend.domain.reservation.converter.ReservationConverter;
import com.space.backend.domain.reservation.dto.ReservationRequest;
import com.space.backend.domain.reservation.dto.ReservationResponse;
import com.space.backend.domain.reservation.service.ReservationService;
import com.space.backend.entity.ReservationEntity;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Business
public class ReservationBusiness {
    private final ReservationService reservationService;
    private final ReservationConverter reservationConverter;

   public ReservationResponse reserveStudyRoom(Long userId, ReservationRequest request) {
       var entity = reservationConverter.toEntity(request);
       var savedEntity= reservationService.reserveStudyRoom(userId, request.getStudyRoomId(), entity);
       return reservationConverter.toResponse(savedEntity);
   }
    public List<Integer> getAvailableTimeSlots(Long studyRoomId, LocalDate date){
       return reservationService.getAvailableTimeSlots(studyRoomId,date);
    }


    public List<ReservationResponse> getMyResList(Long userId) {
       List<ReservationEntity> reservationList = reservationService.getMyReservation(userId);
       return reservationList.stream()
               .map(reservationConverter::toResponse)
               .toList();
    }

    public List<ReservationResponse> getMyAllResList(Long userId) {
        List<ReservationEntity> reservationList = reservationService.getMyAllReservation(userId);
        return reservationList.stream()
                .map(reservationConverter::toResponse)
                .toList();
    }

    public ReservationResponse cancelReservation(Long userId, Long reservationId) {
       var entity = reservationService.cancelReservation(userId,reservationId);
        return reservationConverter.toResponse(entity);

    }
}
