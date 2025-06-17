package com.space.backend.domain.reservation.controller;


import com.space.backend.common.api.Api;
import com.space.backend.domain.reservation.business.ReservationBusiness;
import com.space.backend.domain.reservation.dto.ReservationRequest;
import com.space.backend.domain.reservation.dto.ReservationResponse;
import com.space.backend.domain.spacegroup.business.SpaceGroupBusiness;
import com.space.backend.domain.spacegroup.dto.SpaceGroupRegisterRequest;
import com.space.backend.domain.spacegroup.dto.SpaceGroupResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class ReservationUserApiController {
    private final ReservationBusiness reservationBusiness;
    @PostMapping("/reservations")
    public Api<ReservationResponse> reserve(
            @Valid
            @RequestBody Api<ReservationRequest> request
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        var response = reservationBusiness.reserveStudyRoom(userId,request.getBody());
        return Api.OK(response);
    }
    @GetMapping("/my-reservation")
    public Api<List<ReservationResponse>> myReservation(
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        var response = reservationBusiness.getMyResList(userId);
        return Api.OK(response);
    }
    @GetMapping("/my-all-reservation")
    public Api<List<ReservationResponse>> myAllReservation(
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        var response = reservationBusiness.getMyAllResList(userId);
        return Api.OK(response);
    }
    @PatchMapping("/{reservationId}/cancel")
    public Api<ReservationResponse> cancelReservation(
            @PathVariable Long reservationId
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        var response = reservationBusiness.cancelReservation(userId,reservationId);
        return Api.OK(response);
    }
}
