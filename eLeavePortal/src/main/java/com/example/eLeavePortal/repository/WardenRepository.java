package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.WardenEntity;

@Repository
public interface WardenRepository extends JpaRepository<WardenEntity, Long> {
    
}
