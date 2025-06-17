package com.space.backend.domain.review.controller;


import com.space.backend.common.api.Api;
import com.space.backend.domain.reservation.business.ReservationBusiness;
import com.space.backend.domain.reservation.dto.ReservationRequest;
import com.space.backend.domain.reservation.dto.ReservationResponse;
import com.space.backend.domain.review.business.ReviewBusiness;
import com.space.backend.domain.review.dto.ReviewRequest;
import com.space.backend.domain.review.dto.ReviewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class ReviewUserApiController {
    private final ReviewBusiness reviewBusiness;
    @PostMapping("/reviews")
    public Api<ReviewResponse> writeReview(
            @Valid
            @RequestBody Api<ReviewRequest> request
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        var response = reviewBusiness.writeReview(userId, request.getBody());
        return Api.OK(response);
    }

}
