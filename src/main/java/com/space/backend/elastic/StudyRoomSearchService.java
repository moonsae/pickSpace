package com.space.backend.elastic;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.space.backend.entity.StudyRoomEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.ElasticsearchClient;


@RequiredArgsConstructor
@Service
public class StudyRoomSearchService {

    private final StudyRoomSearchRepository studyRoomSearchRepository;
    private final ElasticsearchClient elasticsearchClient;

    public void indexStudyRoom(StudyRoomEntity entity){
        StudyRoomDocument doc = StudyRoomDocument.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .address(entity.getSpaceGroup().getAddress())
                .status(entity.getStatus())
                .avgRating(entity.getAvgRating())
                .reviewCount(entity.getReviewCount())
                .pricePerHour(entity.getPricePerHour())
                .build();
        studyRoomSearchRepository.save(doc);
    }

    public List<StudyRoomDocument> searchStudyRoom(String keyword) {
        try {
            Query query = MultiMatchQuery.of(m -> m
                    .fields("name", "description", "address")
                    .query(keyword)
            )._toQuery();

            SearchRequest request = SearchRequest.of(s -> s
                    .index("studyroom")
                    .query(query)
                    .size(10000)
            );

            SearchResponse<StudyRoomDocument> response = elasticsearchClient.search(
                    request, StudyRoomDocument.class
            );

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("엘라스틱서치 검색 실패", e);
        }


    }
}
