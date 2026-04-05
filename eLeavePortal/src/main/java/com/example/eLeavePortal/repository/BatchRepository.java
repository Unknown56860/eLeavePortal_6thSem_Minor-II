package com.example.eLeavePortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eLeavePortal.entity.BatchEntity;

@Repository
public interface BatchRepository extends JpaRepository<BatchEntity, Long> {}
