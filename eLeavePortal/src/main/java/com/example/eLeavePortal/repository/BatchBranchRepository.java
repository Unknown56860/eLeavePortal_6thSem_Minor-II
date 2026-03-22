package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.BatchBranchEntity;

@Repository
public interface BatchBranchRepository extends JpaRepository<BatchBranchEntity, Long> {
    
}
