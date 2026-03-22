package com.example.eLeavePortal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eLeavePortal.entity.ApplicationEntity;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
    
    List<ApplicationEntity> findByStudentStudentId(Long studentId);
}
