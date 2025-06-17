package com.space.backend.entity;

import com.space.backend.entity.enums.RoomsStatus;
import com.space.backend.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StudyRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studyGroupId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private Integer pricePerHour;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomsStatus status;

    @Column(nullable = false)
    private BigDecimal avgRating = BigDecimal.ZERO;

    @Column(nullable = false)
    private Integer reviewCount = 0;


    private LocalDateTime registeredAt;
}
