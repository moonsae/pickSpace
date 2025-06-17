package com.space.backend.repository;

import com.space.backend.entity.ReservationEntity;
import com.space.backend.entity.ReviewEntity;
import com.space.backend.entity.StudyRoomEntity;
import com.space.backend.entity.UserEntity;
import com.space.backend.entity.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {


    boolean existsByReservation(ReservationEntity reservation);

    @Query("SELECT r FROM ReviewEntity r JOIN FETCH r.studyRoom WHERE r.id = :id")
    Optional<ReviewEntity> findByIdWithStudyRoom(@Param("id") Long id);

    @Query("SELECT r FROM ReviewEntity r JOIN FETCH r.user WHERE r.id = :id")
    Optional<ReviewEntity> findByIdWithUser(@Param("id") Long id);


    @Query("SELECT r FROM ReviewEntity r " +
            "JOIN FETCH r.user " +
            "JOIN FETCH r.studyRoom " +
            "JOIN FETCH r.reservation " +
            "WHERE r.id = :id")
    Optional<ReviewEntity> findByIdWithAll(Long id);


}
