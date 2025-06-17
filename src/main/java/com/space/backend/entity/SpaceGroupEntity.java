package com.space.backend.entity;

import com.space.backend.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SpaceGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long hostId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private LocalDateTime registeredAt;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "space_group_operating_days", joinColumns = @JoinColumn(name = "space_group_id"))
    @Column(name = "day_of_week", nullable = false)
    private Set<DayOfWeek> operatingDays = new HashSet<>();

    @Column(nullable = false)
    private Integer startSlot; // 예: 9 (09:00 시작)

    @Column(nullable = false)
    private Integer endSlot;   // 예: 18 (18:00 종료, 예약은 17까지 가능)

}
