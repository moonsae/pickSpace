package com.space.backend.batch;

import com.space.backend.entity.ReservationEntity;
import com.space.backend.entity.enums.ReservationStatus;
import com.space.backend.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationBatchService {
    private final ReservationRepository reservationRepository;

    @Scheduled(cron = "0 0 * * * *") // 매시 정각 실행
    public void updateReservationStatusToCompleted() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        int currentHour = now.getHour();

        // 1. 오늘 이전 예약들
        List<ReservationEntity> pastReservations = reservationRepository
                .findAllByStatusAndReservationDateBefore(ReservationStatus.RESERVED, today);

        // 2. 오늘 날짜이고 종료 시간이 현재 시간 이하인 예약들
        List<ReservationEntity> todayReservations = reservationRepository
                .findAllByStatusAndReservationDate(ReservationStatus.RESERVED, today)
                .stream()
                .filter(r -> r.getEndSlot() <= currentHour)
                .toList();

        List<ReservationEntity> toUpdate = new ArrayList<>();
        toUpdate.addAll(pastReservations);
        toUpdate.addAll(todayReservations);

        for (ReservationEntity r : toUpdate) {
            r.setStatus(ReservationStatus.COMPLETED);
        }

        reservationRepository.saveAll(toUpdate);

        log.info("✅ 상태가 COMPLETED로 변경된 예약 수: {}", toUpdate.size());
    }
}
