package com.example.eLeavePortal.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.ApprovalRecordEntity;

@Repository
public interface ApprovalRecordRepository extends JpaRepository<ApprovalRecordEntity, Long> {
    List<ApprovalRecordEntity> findByApplication_ApplicationIdOrderByApprovalDateAsc(Long id);
}
