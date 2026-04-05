package com.example.eLeavePortal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eLeavePortal.entity.StudentEntity;
import com.example.eLeavePortal.entity.StudentFacultyMappingEntity;

@Repository
public interface StudentFacultyMappingRepository extends JpaRepository<StudentFacultyMappingEntity, Long> {
    
    Optional<StudentFacultyMappingEntity> findByStudent(StudentEntity student);
}

