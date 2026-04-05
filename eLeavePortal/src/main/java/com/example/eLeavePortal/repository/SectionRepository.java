package com.example.eLeavePortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eLeavePortal.entity.SectionEntity;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Long> {
    
}
