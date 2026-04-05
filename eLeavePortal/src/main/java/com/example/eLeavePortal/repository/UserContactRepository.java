package com.example.eLeavePortal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eLeavePortal.entity.UserContactEntity;
import com.example.eLeavePortal.entity.UserEntity;

@Repository
public interface UserContactRepository extends JpaRepository<UserContactEntity, Long> {
    
    Optional<UserContactEntity> findByUser(UserEntity user);
}
