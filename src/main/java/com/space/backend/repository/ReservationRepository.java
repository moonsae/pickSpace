package com.space.backend.repository;

import com.space.backend.entity.ReservationEntity;
import com.space.backend.entity.SpaceGroupEntity;
import com.space.backend.entity.StudyRoomEntity;
import com.space.backend.entity.UserEntity;
import com.space.backend.entity.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {


    boolean existsByStudyRoomAndReservationDateAndStartSlotLessThanAndEndSlotGreaterThan(
            StudyRoomEntity studyRoom,
            LocalDate reservationDate,
            Integer endSlot,
            Integer startSlot
    );
    @Query("SELECT r FROM ReservationEntity r " +
            "WHERE r.studyRoom = :studyRoom " +
            "AND r.reservationDate = :date " +
            "AND r.status = 'RESERVED'")
    List<ReservationEntity> findByStudyRoomAndDate(@Param("studyRoom") StudyRoomEntity studyRoom,
                                                   @Param("date") LocalDate date);

    List<ReservationEntity> findByStudyRoomAndReservationDateAndStatus(
            StudyRoomEntity studyRoom, LocalDate reservationDate, ReservationStatus status);

    List<ReservationEntity> findByUserAndStatusOrderByCreatedAtDesc(
            UserEntity userEntity, ReservationStatus status);
    List<ReservationEntity> findByUserOrderByCreatedAtDesc(
            UserEntity userEntity);

    // 오늘 이전 예약
    List<ReservationEntity> findAllByStatusAndReservationDateBefore(ReservationStatus status, LocalDate date);

    // 오늘 예약 중 아직 완료 안된 것들
    List<ReservationEntity> findAllByStatusAndReservationDate(ReservationStatus status, LocalDate date);

}
