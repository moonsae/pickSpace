package com.space.backend.elastic;

import com.space.backend.entity.enums.RoomsStatus;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

@Document(indexName = "studyroom") // Elasticsearch 인덱스명
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyRoomDocument {

    @Id
    private Long id;  // 스터디룸 ID

    private String name; // 스터디룸 이름

    private String description; // 설명

    private String address; // 업체 주소

    private RoomsStatus status; // APPROVED 상태만 색인화할 예정

    private BigDecimal avgRating; // 평균 별점

    private Integer reviewCount; // 리뷰 수

    private Integer pricePerHour; // 가격

}