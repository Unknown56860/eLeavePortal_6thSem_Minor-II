package com.example.eLeavePortal.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.*;
import jakarta.persistence.*;

import com.example.eLeavePortal.enums.ApplicationStatusEnums;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, length = 500)
    private String reason;

    @Column(length = 1500)
    private String explanation;

    @Column(length = 1000)
    private String attachmentUrl;

    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private UserEntity updatedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatusEnums currentStatus;
}
