package com.space.backend.elastic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.space.backend.entity.enums.RoomsStatus;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Document(indexName = "studyroom") // Elasticsearch 인덱스명
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomDocument {

    @Id
    private Long id;  // 스터디룸 ID

    @Field(type = FieldType.Text, analyzer = "my_custom_analyzer")
    private String name; // 스터디룸 이름
    @Field(type = FieldType.Text, analyzer = "my_custom_analyzer")
    private String description; // 설명
    @Field(type = FieldType.Text, analyzer = "my_custom_analyzer")
    private String address; // 업체 주소

    private RoomsStatus status; // APPROVED 상태만 색인화할 예정

    private BigDecimal avgRating; // 평균 별점

    private Integer reviewCount; // 리뷰 수

    private Integer pricePerHour; // 가격

}