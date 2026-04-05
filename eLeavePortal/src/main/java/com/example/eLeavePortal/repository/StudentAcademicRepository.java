package com.example.eLeavePortal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eLeavePortal.entity.StudentAcademicProfileEntity;
import com.example.eLeavePortal.entity.StudentEntity;

@Repository
public interface StudentAcademicRepository extends JpaRepository<StudentAcademicProfileEntity, Long> {
    
    Optional<StudentAcademicProfileEntity> findByStudent(StudentEntity student);
}
