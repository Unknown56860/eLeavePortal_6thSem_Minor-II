package com.example.eLeavePortal.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

import com.example.eLeavePortal.enums.ApplicationStatusEnums;
import com.example.eLeavePortal.enums.RolesEnum;

@Entity
@Table(name = "leave_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, length = 100)
    private String leaveReason;

    @Column(length = 500)
    private String description;

    @Column
    private String attachmentUrl;

    @Column(nullable = false)
    private LocalDateTime lastUpdatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolesEnum lastUpdatedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatusEnums status;
}