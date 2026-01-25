package com.example.eLeavePortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.ContactEntity;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {}
