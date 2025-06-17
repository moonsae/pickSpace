package com.space.backend.repository;

import com.space.backend.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findFirstById(Long userId);

    Optional<UserEntity> findFirstByEmailAndPasswordOrderByIdDesc(String email, String password);

    Optional<UserEntity> findFirstByEmailOrderByIdDesc(String email);
}
