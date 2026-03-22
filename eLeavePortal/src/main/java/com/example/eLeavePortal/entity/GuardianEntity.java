package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "guardians")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuardianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guardianId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @OneToOne
    @JoinColumn(name = "user_detail_id", nullable = false)
    private UserDetailEntity userDetail;

    @Column(nullable = false)
    private String relation;

    @Column(nullable = false)
    private String address;
}