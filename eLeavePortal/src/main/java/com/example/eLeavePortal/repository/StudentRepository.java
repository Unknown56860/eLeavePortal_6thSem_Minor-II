package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.StudentEntity;
import com.example.eLeavePortal.entity.UserEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    
    Optional<StudentEntity> findByUser(UserEntity user);
    
    Optional<StudentEntity> findByUser_UserName(String username);
}
