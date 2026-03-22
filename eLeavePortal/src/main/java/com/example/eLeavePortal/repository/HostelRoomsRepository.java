package com.example.eLeavePortal.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.HostelRoomsEntity;

@Repository
public interface HostelRoomsRepository extends JpaRepository<HostelRoomsEntity, Long> {
    
}
