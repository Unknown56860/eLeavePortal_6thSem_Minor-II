package com.example.eLeavePortal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.eLeavePortal.dto.ApplicationTrendDto;
import com.example.eLeavePortal.entity.ApplicationEntity;
import com.example.eLeavePortal.enums.ApplicationStatusEnums;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    long countByCurrentStatusNotIn(List<ApplicationStatusEnums> statuses);
    
    List<ApplicationEntity> findByStudent_User_UserName(String username);


    @Query("""
        SELECT new com.example.eLeavePortal.dto.ApplicationTrendDto(a.startDate, COUNT(a))
        FROM ApplicationEntity a WHERE a.currentStatus NOT IN (:excluded)
        GROUP BY a.startDate ORDER BY a.startDate
    """)
    List<ApplicationTrendDto> getApplicationTrend(List<ApplicationStatusEnums> excluded);
}
