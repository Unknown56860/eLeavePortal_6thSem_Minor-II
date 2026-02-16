package com.example.eLeavePortal.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
