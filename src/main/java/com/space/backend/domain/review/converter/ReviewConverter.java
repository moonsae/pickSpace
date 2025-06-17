package com.space.backend.domain.review.converter;

import com.space.backend.common.annotation.Converter;
import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.review.dto.ReviewRequest;
import com.space.backend.domain.review.dto.ReviewResponse;
import com.space.backend.domain.user.dto.UserRegisterRequest;
import com.space.backend.domain.user.dto.UserResponse;
import com.space.backend.entity.ReviewEntity;
import com.space.backend.entity.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class ReviewConverter {
    public ReviewEntity toEntity(ReviewRequest request){
        return Optional.ofNullable(request)
                .map(it->{
                    return ReviewEntity.builder()
                            .content(request.getContent())
                            .rating(request.getRating())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT,"ReviewRequest Null"));
    }
    public ReviewResponse toResponse(ReviewEntity reviewEntity){
        return Optional.ofNullable(reviewEntity)
                .map(it->{
                    return ReviewResponse.builder()
                            .reviewId(reviewEntity.getId())
                            .userId(reviewEntity.getUser().getId())
                            .username(reviewEntity.getUser().getName())
                            .reservationId(reviewEntity.getReservation().getId())
                            .reservationDate(reviewEntity.getReservation().getReservationDate())
                            .startSlot(reviewEntity.getReservation().getStartSlot())
                            .endSlot(reviewEntity.getReservation().getEndSlot())
                            .studyRoomId(reviewEntity.getStudyRoom().getId())
                            .studyRoomName(reviewEntity.getStudyRoom().getName())
                            .rating(reviewEntity.getRating())
                            .content(reviewEntity.getContent())
                            .createdAt(reviewEntity.getCreatedAt())
                            .reply(reviewEntity.getReply())
                            .repliedAt(reviewEntity.getRepliedAt())
                            .build()
                            ;
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"ReviewEntity Null"));
    }
}

