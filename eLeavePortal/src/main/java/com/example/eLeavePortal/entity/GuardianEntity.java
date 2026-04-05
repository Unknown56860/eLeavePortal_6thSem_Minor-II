package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "guardians")
@Getter
@Setter
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
    private UserContactEntity userDetail;

    @Column(nullable = false)
    private String relation;

    @Column(nullable = false)
    private String address;
}