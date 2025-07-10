package com.space.backend;

import com.space.backend.domain.studyroom.business.StudyRoomBusiness;
import com.space.backend.domain.studyroom.service.StudyRoomService;
import com.space.backend.elastic.StudyRoomDocument;
import com.space.backend.elastic.StudyRoomSearchRepository;
import com.space.backend.elastic.StudyRoomSearchService;
import com.space.backend.entity.SpaceGroupEntity;
import com.space.backend.entity.StudyRoomEntity;
import com.space.backend.entity.enums.RoomsStatus;
import com.space.backend.repository.SpaceGroupRepository;
import com.space.backend.repository.StudyRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DisplayName("Elasticsearch vs RDB 검색 성능 비교 테스트")
@Slf4j
public class StudyRoomSearchPerformanceTest {
/*
    @Autowired
    private StudyRoomBusiness studyRoomBusiness;



    @Test
    void searchPerformanceComparison() {
        String keyword = "깨끗"; // 예시 키워드

        // RDB 검색 시간 측정
        long rdbStart = System.currentTimeMillis();
        List<StudyRoomEntity> rdbResults = studyRoomBusiness.searchWithJoin(keyword);
        long rdbEnd = System.currentTimeMillis();
        long rdbDuration = rdbEnd - rdbStart;

        // Elasticsearch 검색 시간 측정
        long esStart = System.currentTimeMillis();
        List<StudyRoomDocument> esResults = studyRoomBusiness.search(keyword);
        long esEnd = System.currentTimeMillis();
        long esDuration = esEnd - esStart;

        System.out.println("🔍 RDB 검색 소요 시간: " + rdbDuration + "ms, 결과 수: " + rdbResults.size());
        System.out.println("🔍 Elasticsearch 검색 소요 시간: " + esDuration + "ms, 결과 수: " + esResults.size());
    }*/
}
