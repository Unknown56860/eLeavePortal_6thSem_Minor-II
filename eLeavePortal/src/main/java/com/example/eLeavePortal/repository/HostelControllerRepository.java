package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.HostelControllerEntity;
import com.example.eLeavePortal.entity.UserEntity;

@Repository
public interface HostelControllerRepository extends JpaRepository<HostelControllerEntity, Long> {
    
    Optional<HostelControllerEntity> findByUser(UserEntity user);
}
