package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.ClassTeacherEntity;
import com.example.eLeavePortal.entity.UserEntity;

@Repository
public interface ClassTeacherRepository
        extends JpaRepository<ClassTeacherEntity, Long> {

    Optional<ClassTeacherEntity> findByUser(UserEntity user);

}
