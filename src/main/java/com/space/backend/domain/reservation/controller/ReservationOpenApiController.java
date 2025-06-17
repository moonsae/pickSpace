package com.space.backend.domain.reservation.controller;


import com.space.backend.common.api.Api;
import com.space.backend.domain.reservation.business.ReservationBusiness;
import com.space.backend.domain.reservation.dto.ReservationRequest;
import com.space.backend.domain.reservation.dto.ReservationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/")
public class ReservationOpenApiController {
    private final ReservationBusiness reservationBusiness;
    @GetMapping("/available-times")
    public Api<List<Integer>> getAvailableTimes(
            @RequestParam("studyRoomId") Long studyRoomId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ){
        List<Integer> response = reservationBusiness.getAvailableTimeSlots(studyRoomId, date);
        return Api.OK(response);
    }
}
