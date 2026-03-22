package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.BatchBranchSectionEntity;

@Repository
public interface BatchBranchSectionRepository extends JpaRepository<BatchBranchSectionEntity, Long> {
    
}
