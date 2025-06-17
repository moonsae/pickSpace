package com.space.backend.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private Long reviewId;

    // 작성자 정보
    private Long userId;
    private String username;

    // 예약 정보
    private Long reservationId;
    private LocalDate reservationDate;
    private Integer startSlot;
    private Integer endSlot;

    // 스터디룸 정보
    private Long studyRoomId;
    private String studyRoomName;

    // 리뷰 정보
    private Integer rating;
    private String content;
    private LocalDateTime createdAt;

    // host 답글
    private String reply;
    private LocalDateTime repliedAt;
}
