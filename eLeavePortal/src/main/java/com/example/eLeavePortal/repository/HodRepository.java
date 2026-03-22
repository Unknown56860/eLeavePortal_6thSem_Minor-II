package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.HodEntity;

@Repository
public interface HodRepository extends JpaRepository<HodEntity, Long> {
    
}
