package com.space.backend;

import com.space.backend.domain.studyroom.service.StudyRoomService;
import com.space.backend.elastic.StudyRoomDocument;
import com.space.backend.elastic.StudyRoomSearchService;
import com.space.backend.entity.StudyRoomEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@DisplayName("RDB 검색 기능 테스트")
class StudyRoomRdbSearchTest {

   /* @Autowired
    private StudyRoomService studyRoomService;

    @Test
    void searchWithJoin_shouldReturnResults_whenKeywordMatches() {
        // given
        String keyword = "강남"; // 실제 존재할 법한 테스트 키워드

        // when
        List<StudyRoomEntity> results = studyRoomService.searchWithJoin(keyword);

        // then
        System.out.println("RDB 검색 결과 수: " + results.size());
        results.forEach(room -> {
            System.out.println("이름: " + room.getName() + ", 주소: " + room.getSpaceGroup().getAddress());
        });

        // 단순 검증 (예: 검색 결과가 0이 아닌지 확인)
        assertFalse(results.isEmpty(), "검색 결과가 존재해야 합니다.");
    }*/
}
