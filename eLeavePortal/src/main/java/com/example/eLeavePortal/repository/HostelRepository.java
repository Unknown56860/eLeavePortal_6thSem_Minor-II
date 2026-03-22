package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.HostelEntity;

@Repository
public interface HostelRepository extends JpaRepository<HostelEntity, Long> {
    
}
