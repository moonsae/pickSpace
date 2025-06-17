package com.space.backend.domain.review.service;

import com.space.backend.common.error.*;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.review.dto.ReviewRequest;
import com.space.backend.entity.*;
import com.space.backend.entity.enums.ReservationStatus;
import com.space.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;
    private final SpaceGroupRepository spaceGroupRepository;

    @Transactional
    public ReviewEntity writeReview(Long userId, Long reservationId, ReviewEntity reviewEntity){
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()->new ApiException(ReservationErrorCode.RESERVATION_NOT_FOUND));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()->new ApiException(UserErrorCode.USER_NOT_FOUND));
        if(!reservation.getUser().getId().equals(userId)){
            throw new ApiException(ReservationErrorCode.RESERVATION_NOT_ALLOWED,"본인이 예약한 리뷰만 작성이 가능합니다");
        }
        if(reservation.getStatus() != ReservationStatus.COMPLETED){
            throw new ApiException(ReservationErrorCode.RESERVATION_NOT_FOUND,"이용 완료된 예약만 리뷰가 가능합니다");
        }
        //이미 리뷰를 작성했는가?
        if(reviewRepository.existsByReservation(reservation)){
            throw new ApiException(ReservationErrorCode.RESERVATION_NOT_FOUND,"이미 리뷰를 작성했습니다");
        }
        return Optional.ofNullable(reviewEntity)
                .map(it->{
                    StudyRoomEntity studyRoom = reservation.getStudyRoom();

                    // 기존 데이터 추출
                    int prevCount = studyRoom.getReviewCount();
                    BigDecimal prevAvg = studyRoom.getAvgRating();

                    // 새로운 평균 계산
                    BigDecimal newTotal = prevAvg.multiply(BigDecimal.valueOf(prevCount))
                            .add(BigDecimal.valueOf(it.getRating()));
                    int newCount = prevCount + 1;
                    BigDecimal newAvg = newTotal.divide(BigDecimal.valueOf(newCount), 2, RoundingMode.HALF_UP);

                    // 리뷰 저장
                    it.setUser(user);
                    it.setStudyRoom(studyRoom);
                    it.setReservation(reservation);
                    it.setCreatedAt(LocalDateTime.now());

                    // 리뷰 저장 후
                    ReviewEntity savedReview = reviewRepository.save(it);

                    // 스터디룸 업데이트
                    studyRoom.setAvgRating(newAvg);
                    studyRoom.setReviewCount(newCount);
                    studyRoomRepository.save(studyRoom);

                    return savedReview;
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "reviewEntity is null"));

    }
    @Transactional
    public ReviewEntity replyToReview(Long hostId, Long reviewId, String replyContent){
        ReviewEntity review = reviewRepository.findByIdWithAll(reviewId)
                .orElseThrow(() -> new ApiException(ReviewErrorCode.REVIEW_ERROR_CODE));

        StudyRoomEntity studyRoom = review.getStudyRoom();
        Long studyGroupId = studyRoom.getStudyGroupId();
        SpaceGroupEntity spaceGroup = spaceGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new ApiException(RoomErrorCode.ROOM_ERROR_CODE));
        if (!spaceGroup.getHostId().equals(hostId)) {
            throw new ApiException(ErrorCode.NULL_POINT, "해당 리뷰에 답글을 남길 권한이 없습니다.");
        }

        if (review.getReply() != null) {
            throw new ApiException(ReviewErrorCode.ALREADY_REPLIED);
        }
        return Optional.ofNullable(review)
                .map(it->{
                    review.setReply(replyContent);
                    review.setRepliedAt(LocalDateTime.now());
                    return reviewRepository.save(it);
                }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"Reply null"));
    }
}

