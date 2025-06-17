package com.space.backend.domain.reservation.converter;

import com.space.backend.common.annotation.Converter;
import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.reservation.dto.ReservationRequest;
import com.space.backend.domain.reservation.dto.ReservationResponse;
import com.space.backend.domain.spacegroup.dto.SpaceGroupRegisterRequest;
import com.space.backend.domain.spacegroup.dto.SpaceGroupResponse;
import com.space.backend.entity.ReservationEntity;
import com.space.backend.entity.SpaceGroupEntity;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Converter
public class ReservationConverter {
    public ReservationEntity toEntity(ReservationRequest request){
        return Optional.ofNullable(request)
                .map(it->{
                    return ReservationEntity.builder()
                            .reservationDate(request.getReservationDate())
                            .startSlot(request.getStartSlot())
                            .endSlot(request.getEndSlot())
                            .build();
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"ReservationRequest NULL"));

    }

    public ReservationResponse toResponse(ReservationEntity reservationEntity){
        return Optional.ofNullable(reservationEntity)
                .map(it->{

                    return ReservationResponse.builder()
                            .reservationId(it.getId())
                            .studyRoomId(it.getStudyRoom().getId())
                            .studyRoomName(it.getStudyRoom().getName())
                            .reservationDate(it.getReservationDate())
                            .startSlot(it.getStartSlot())
                            .endSlot(it.getEndSlot())
                            .totalPrice(it.getTotalPrice())
                            .status(it.getStatus())
                            .build();
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"reservationEntity NULL"));
    }

}
