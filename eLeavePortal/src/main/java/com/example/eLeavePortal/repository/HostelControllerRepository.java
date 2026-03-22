package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.HostelControllerEntity;

@Repository
public interface HostelControllerRepository extends JpaRepository<HostelControllerEntity, Long> {
    
}
