package com.space.backend.domain.review.business;

import com.space.backend.common.annotation.Business;
import com.space.backend.common.error.UserErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.review.converter.ReviewConverter;
import com.space.backend.domain.review.dto.ReviewReplyRequest;
import com.space.backend.domain.review.dto.ReviewRequest;
import com.space.backend.domain.review.dto.ReviewResponse;
import com.space.backend.domain.review.service.ReviewService;
import com.space.backend.domain.token.business.TokenBusiness;
import com.space.backend.domain.token.dto.TokenResponse;
import com.space.backend.domain.user.converter.UserConverter;
import com.space.backend.domain.user.dto.UserLoginRequest;
import com.space.backend.domain.user.dto.UserRegisterRequest;
import com.space.backend.domain.user.dto.UserResponse;
import com.space.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Business
public class ReviewBusiness {
    private final ReviewService reviewService;
    private final ReviewConverter reviewConverter;
    public ReviewResponse writeReview(Long userId, ReviewRequest request){
        Long reservationId = request.getReservationId();
        var entity = reviewConverter.toEntity(request);
        var newEntity = reviewService.writeReview(userId,reservationId,entity);
        var response = reviewConverter.toResponse(newEntity);
        return response;
    }
    public ReviewResponse replyToReview(Long reviewId, ReviewReplyRequest request, Long hostId){
        var entity = reviewService.replyToReview(hostId, reviewId,request.getReply());
        var response = reviewConverter.toResponse(entity);
        return response;
    }
}
