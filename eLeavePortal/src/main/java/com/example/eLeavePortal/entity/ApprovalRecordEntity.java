package com.example.eLeavePortal.entity;

import java.time.LocalDate;

import lombok.*;
import jakarta.persistence.*;

import com.example.eLeavePortal.enums.RolesEnum;
import com.example.eLeavePortal.enums.ApplicationStatusEnums;

@Entity
@Table(name = "approval_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approvalId;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private ApplicationEntity application;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolesEnum role;

    @Column(length = 500)
    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatusEnums approvalStatus;

    @Column(nullable = false)
    private LocalDate approvalDate;
}
