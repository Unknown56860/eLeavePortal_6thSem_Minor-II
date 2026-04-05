package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.UserEntity;
import com.example.eLeavePortal.entity.WardenEntity;

@Repository
public interface WardenRepository extends JpaRepository<WardenEntity, Long> {
    
    Optional<WardenEntity> findByUser(UserEntity user);
}
