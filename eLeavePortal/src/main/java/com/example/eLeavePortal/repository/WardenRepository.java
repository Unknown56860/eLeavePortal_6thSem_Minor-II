package com.example.eLeavePortal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.UserEntity;
import com.example.eLeavePortal.entity.WardenEntity;
import com.example.eLeavePortal.entity.HostelControllerEntity;

@Repository
public interface WardenRepository extends JpaRepository<WardenEntity, Long> {
    
    Optional<WardenEntity> findByUser(UserEntity user);

    List<WardenEntity> findByHostelController(HostelControllerEntity controller);
}
