package com.space.backend.repository;

import com.space.backend.entity.SpaceGroupEntity;
import com.space.backend.entity.StudyRoomEntity;
import com.space.backend.entity.enums.RoomsStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyRoomRepository extends JpaRepository<StudyRoomEntity, Long> {
    List<StudyRoomEntity> findAllByStatusOrderByRegisteredAtDesc(RoomsStatus status);

    List<StudyRoomEntity> findAllByStudyGroupIdAndStatusOrderByRegisteredAtDesc(Long studyGroupId, RoomsStatus status);


    Optional<StudyRoomEntity> findGroupById(Long studyGroupId);
}
