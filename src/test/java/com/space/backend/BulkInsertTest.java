package com.space.backend;

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
@DisplayName("✅ 데이터 10만 건 삽입 및 색인")
@Slf4j
public class BulkInsertTest {

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private StudyRoomSearchRepository studyRoomSearchRepository;

    @Autowired
    private SpaceGroupRepository spaceGroupRepository;

    @Autowired
    private StudyRoomSearchService studyRoomSearchService;



    @Test
    public void insertBulkData() {
        SpaceGroupEntity spaceGroup = spaceGroupRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("❌ SpaceGroup ID 1 없음"));

        int count = 100_000;
        List<StudyRoomEntity> bulk = new ArrayList<>();

        int success = 0;
        int fail = 0;

        for (int i = 1; i <= count; i++) {
            StudyRoomEntity room = StudyRoomEntity.builder()
                    .name("스터디룸 " + i)
                    .description("강남역과 5분 거리이며, 깨끗합니다. " + i)
                    .maxCapacity(10)
                    .pricePerHour(10000)
                    .status(RoomsStatus.APPROVED)
                    .avgRating(BigDecimal.valueOf(4.5))
                    .reviewCount(i % 100)
                    .registeredAt(LocalDateTime.now())
                    .spaceGroup(spaceGroup)
                    .build();

            bulk.add(room);

            if (i % 10_000 == 0) {
                try {
                    studyRoomRepository.saveAll(bulk);
                    for (StudyRoomEntity savedRoom : bulk) {
                        try {
                            studyRoomSearchService.indexStudyRoom(savedRoom);
                            success++;
                        } catch (Exception e) {
                            log.error("❌ 색인 실패 - ID: {}, 에러: {}", savedRoom.getId(), e.getMessage());
                            fail++;
                        }
                    }
                    log.info("✅ {}건 저장 및 색인 완료", i);
                } catch (Exception e) {
                    log.error("❌ RDB 저장 실패: {}", e.getMessage());
                }
                bulk.clear();
            }
        }

        // 남은 데이터 처리
        if (!bulk.isEmpty()) {
            try {
                studyRoomRepository.saveAll(bulk);
                for (StudyRoomEntity savedRoom : bulk) {
                    try {
                        studyRoomSearchService.indexStudyRoom(savedRoom);
                        success++;
                    } catch (Exception e) {
                        log.error("❌ 색인 실패 - ID: {}, 에러: {}", savedRoom.getId(), e.getMessage());
                        fail++;
                    }
                }
            } catch (Exception e) {
                log.error("❌ RDB 저장 실패 (잔여): {}", e.getMessage());
            }
        }

        log.info("🎉 총 {}건 저장 및 색인 완료 (성공: {}, 실패: {})", count, success, fail);
    }
}
