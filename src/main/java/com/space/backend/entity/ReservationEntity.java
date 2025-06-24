package com.space.backend.entity;

import com.space.backend.entity.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private StudyRoomEntity studyRoom;

    private LocalDate reservationDate;

    @Column(nullable = false)
    private Integer startSlot;  // 예: 13 (13시)
    @Column(nullable = false)
    private Integer endSlot;    // 예: 15 (15시)

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime canceledAt; // optional

    @Column(nullable = false)
    private Integer totalPrice;

}
