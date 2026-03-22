package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "student_personal_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentPersonalProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentPersonalProfileId;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private StudentEntity student;

    @Column(nullable = false)
    private String homeAddress;
}
