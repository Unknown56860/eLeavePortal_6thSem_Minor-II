package com.example.eLeavePortal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.example.eLeavePortal.entity.UserEntity;
import com.example.eLeavePortal.enums.ApplicationStatusEnums;

import lombok.Data;

@Data
public class ApplicationDto {

    private Long id;

    private String username;
    private String studentName;
    private String studentRoll;
    private String studentPfp;
    
    private LocalDate startDate;
    private LocalDate endDate;

    private String reason;
    private String explanation;

    private String attachmentUrl;
    private MultipartFile attachmentFile;

    private LocalDateTime lastUpdate;
    private UserEntity updatedBy;

    private ApplicationStatusEnums currentStatus;
}
