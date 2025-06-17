package com.space.backend.domain.review.controller;


import com.space.backend.common.api.Api;
import com.space.backend.domain.review.business.ReviewBusiness;
import com.space.backend.domain.review.dto.ReviewReplyRequest;
import com.space.backend.domain.review.dto.ReviewRequest;
import com.space.backend.domain.review.dto.ReviewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/host/reviews")
public class ReviewHostApiController {
    private final ReviewBusiness reviewBusiness;
    @PatchMapping("/{reviewId}/repl")
    public Api<ReviewResponse> writeReview(
            @PathVariable Long reviewId,
            @Valid
            @RequestBody Api<ReviewReplyRequest> request
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long hostId = (Long) authentication.getPrincipal();
        var response = reviewBusiness.replyToReview(reviewId, request.getBody(),hostId);
        return Api.OK(response);
    }


}
