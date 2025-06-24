package com.space.backend.domain.reservation.service;

import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.error.ReservationErrorCode;
import com.space.backend.common.error.RoomErrorCode;
import com.space.backend.common.error.UserErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.domain.reservation.dto.AvailableTimeResponse;
import com.space.backend.domain.reservation.dto.ReservationRequest;
import com.space.backend.entity.ReservationEntity;
import com.space.backend.entity.SpaceGroupEntity;
import com.space.backend.entity.StudyRoomEntity;
import com.space.backend.entity.UserEntity;
import com.space.backend.entity.enums.ReservationStatus;
import com.space.backend.repository.ReservationRepository;
import com.space.backend.repository.SpaceGroupRepository;
import com.space.backend.repository.StudyRoomRepository;
import com.space.backend.repository.UserRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;
    private final SpaceGroupRepository spaceGroupRepository;

    public ReservationEntity reserveStudyRoom(Long userId, Long studyRoomId, ReservationEntity reservationEntity){
        //유저 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()->new ApiException(UserErrorCode.USER_NOT_FOUND));
        //스터디룸 조회
        StudyRoomEntity studyRoom = studyRoomRepository.findById(studyRoomId)
                .orElseThrow(() -> new ApiException(RoomErrorCode.ROOM_ERROR_CODE));
        // 해당 스터디룸이 속한 업체(공간) 조회
        SpaceGroupEntity spaceGroup = spaceGroupRepository.findByIdWithOperatingDays(studyRoom.getSpaceGroup().getId())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "업체 정보가 존재하지 않습니다."));
        ;
        LocalDate reservationDate = reservationEntity.getReservationDate();
        Integer startSlot = reservationEntity.getStartSlot();
        Integer endSlot = reservationEntity.getEndSlot();

        // 당일 또는 이전 날짜 예약은 불가
        if(!reservationDate.isAfter(LocalDate.now())){
            throw new ApiException(RoomErrorCode.INVALID_RESERVATION_DATE);
        }

        // 예약한 요일이 영업일이 아닌 경우
        DayOfWeek dayOfWeek = reservationDate.getDayOfWeek();
        if(!spaceGroup.getOperatingDays().contains(dayOfWeek)){
            throw new ApiException(RoomErrorCode.NOT_OPERATING_DAY);
        }

        // 예약 시간 유효성 검사
        if(startSlot < spaceGroup.getStartSlot() || endSlot > spaceGroup.getEndSlot()){
            throw new ApiException(RoomErrorCode.INVALID_TIME_SLOT,"운영 시간 외에는 예약할 수 없습니다.");
        }
        if (startSlot >= endSlot) {
            throw new ApiException(RoomErrorCode.INVALID_TIME_SLOT);
        }
        //예약 중복 확인
        boolean exists = reservationRepository.existsByStudyRoomAndReservationDateAndStartSlotLessThanAndEndSlotGreaterThan(
                studyRoom, reservationEntity.getReservationDate(), endSlot, startSlot
        );
        if (exists) {
            throw new ApiException(RoomErrorCode.ALREADY_RESERVE);
        }
        // 총 가격 계산
        int totalPrice = (endSlot - startSlot) * studyRoom.getPricePerHour();

        return Optional.ofNullable(reservationEntity)
                .map(it-> {
                    reservationEntity.setUser(user);
                    reservationEntity.setStudyRoom(studyRoom);
                    reservationEntity.setTotalPrice(totalPrice);
                    reservationEntity.setStatus(ReservationStatus.RESERVED);
                    reservationEntity.setCreatedAt(LocalDateTime.now());
                    return reservationRepository.save(it);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"ReservationEntity null"));
    }
    public List<Integer> getAvailableTimeSlots(Long studyRoomId, LocalDate date) {
        // 1. 스터디룸 조회
        StudyRoomEntity studyRoom = studyRoomRepository.findById(studyRoomId)
                .orElseThrow(() -> new ApiException(RoomErrorCode.ROOM_ERROR_CODE));

        // 2. 스터디룸의 소속 업체 조회 → 운영 요일/시간
        SpaceGroupEntity spaceGroup = spaceGroupRepository.findByIdWithOperatingDays(studyRoom.getSpaceGroup().getId())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "업체 정보가 존재하지 않습니다."));

        if (!spaceGroup.getOperatingDays().contains(date.getDayOfWeek())) {
            return List.of(); // 영업하지 않는 날이면 빈 리스트
        }

        int startSlot = spaceGroup.getStartSlot();
        int endSlot = spaceGroup.getEndSlot(); // exclusive

        // 3. 이미 예약된 예약 리스트 조회
        List<ReservationEntity> reservations = reservationRepository
                .findByStudyRoomAndReservationDateAndStatus(
                        studyRoom, date, ReservationStatus.RESERVED);

        // 4. 예약된 시간 구간 집계
        Set<Integer> reservedSlots = new HashSet<>();
        for (ReservationEntity reservation : reservations) {
            for (int i = reservation.getStartSlot(); i < reservation.getEndSlot(); i++) {
                reservedSlots.add(i);
            }
        }

        // 5. 가능한 슬롯만 필터링해서 반환
        List<Integer> availableSlots = new ArrayList<>();
        for (int i = startSlot; i < endSlot; i++) {
            if (!reservedSlots.contains(i)) {
                availableSlots.add(i);
            }
        }

        return availableSlots;
    }


    public List<ReservationEntity> getMyReservation(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()->new ApiException(UserErrorCode.USER_NOT_FOUND));
        return reservationRepository.findByUserAndStatusOrderByCreatedAtDesc(user,ReservationStatus.RESERVED);

    }

    public List<ReservationEntity> getMyAllReservation(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()->new ApiException(UserErrorCode.USER_NOT_FOUND));
        return reservationRepository.findByUserOrderByCreatedAtDesc(user);

    }
    public ReservationEntity cancelReservation(Long userId, Long reservationId){
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(()->new ApiException(ReservationErrorCode.RESERVATION_NOT_FOUND));
        if(!reservationEntity.getUser().getId().equals(userId)){
            throw new ApiException(ReservationErrorCode.RESERVATION_NOT_ALLOWED);
        }
        if(reservationEntity.getStatus() == ReservationStatus.CANCELED){
            throw new ApiException(ReservationErrorCode.ALREADY_CANCELED);
        }
        LocalDate today = LocalDate.now();
        if(!reservationEntity.getReservationDate().isAfter(today)){
            throw new ApiException(ReservationErrorCode.CANCEL_NOT_ALLOWED);
        }
        return Optional.ofNullable(reservationEntity)
                .map(it->{
                    reservationEntity.setStatus(ReservationStatus.CANCELED);
                    reservationEntity.setCanceledAt(LocalDateTime.now());
                    return reservationRepository.save(reservationEntity);

                }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"ReservationEntity is null"));

    }
}

