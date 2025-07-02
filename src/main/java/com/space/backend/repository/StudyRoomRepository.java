package com.space.backend.repository;

import com.space.backend.entity.SpaceGroupEntity;
import com.space.backend.entity.StudyRoomEntity;
import com.space.backend.entity.enums.RoomsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface StudyRoomRepository extends JpaRepository<StudyRoomEntity, Long> {
    List<StudyRoomEntity> findAllByStatusOrderByRegisteredAtDesc(RoomsStatus status);

    List<StudyRoomEntity> findAllBySpaceGroupAndStatusOrderByRegisteredAtDesc(SpaceGroupEntity spaceGroup, RoomsStatus status);

    Optional<StudyRoomEntity> findGroupById(Long studyGroupId);

    List<StudyRoomEntity> findAllByStatus(RoomsStatus roomsStatus);

    @Query("SELECT s FROM StudyRoomEntity s WHERE " +
            "s.name LIKE %:keyword% OR s.description LIKE %:keyword% OR s.spaceGroup.address LIKE %:keyword%")
    List<StudyRoomEntity> searchWithJoin(@Param("keyword") String keyword);
}
