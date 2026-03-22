package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.ClassTeacherEntity;

@Repository
public interface ClassTeacherRepository extends JpaRepository<ClassTeacherEntity, Long> {
    
}
