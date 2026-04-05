package com.example.eLeavePortal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eLeavePortal.entity.StudentEntity;
import com.example.eLeavePortal.entity.StudentPersonalProfileEntity;

@Repository
public interface StudentPersonalRepository extends JpaRepository<StudentPersonalProfileEntity, Long> {

    Optional<StudentPersonalProfileEntity> findByStudent(StudentEntity student);

}
