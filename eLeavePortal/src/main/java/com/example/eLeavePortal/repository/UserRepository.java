package com.example.eLeavePortal.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eLeavePortal.entity.UserEntity;
import java.util.List;
import com.example.eLeavePortal.enums.RolesEnum;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
    
    boolean existsByUserName(String userName);

    long countByRole(RolesEnum role);

    List<UserEntity> findByRole(RolesEnum role);
    List<UserEntity> findByRoleIn(List<RolesEnum> roles);
}
