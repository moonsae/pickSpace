package com.space.backend.repository;

import com.space.backend.entity.SpaceGroupEntity;
import com.space.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpaceGroupRepository extends JpaRepository<SpaceGroupEntity,Long> {

    @Query("SELECT sg FROM SpaceGroupEntity sg LEFT JOIN FETCH sg.operatingDays WHERE sg.id = :id")
    Optional<SpaceGroupEntity> findByIdWithOperatingDays(@Param("id") Long id);

}
