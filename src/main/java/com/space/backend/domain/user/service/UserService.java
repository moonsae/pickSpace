package com.space.backend.domain.user.service;

import com.space.backend.common.error.ErrorCode;
import com.space.backend.common.error.UserErrorCode;
import com.space.backend.common.exception.ApiException;
import com.space.backend.entity.UserEntity;
import com.space.backend.entity.enums.UserRole;
import com.space.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity userRegister(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it->{
                    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    userEntity.setRole(UserRole.ROLE_USER);
                    return userRepository.save(userEntity);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"UserEntity 비어있음"));
    }
    public UserEntity hostRegister(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it->{
                    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    userEntity.setRole(UserRole.ROLE_HOST);
                    return userRepository.save(userEntity);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"UserEntity 비어있음"));
    }
    public UserEntity adminRegister(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it->{
                    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    userEntity.setRole(UserRole.ROLE_ADMIN);
                    return userRepository.save(userEntity);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"UserEntity 비어있음"));
    }
    @Transactional
    public UserEntity login(String email, String rawPassword) {
        // 이메일로 사용자 조회
        UserEntity entity = userRepository.findFirstByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        // 비밀번호 검증
        if (!passwordEncoder.matches(rawPassword, entity.getPassword())) {
            throw new ApiException(UserErrorCode.INVALID_PASSWORD);
        }

        // 로그인 시간 갱신
        entity.setLastLoginAt(LocalDateTime.now());
        return userRepository.save(entity);
    }

    public UserEntity getUserWithThrow(
            Long userId
    ){
        return userRepository.findFirstById(
                userId
        ).orElseThrow(()->new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

}
