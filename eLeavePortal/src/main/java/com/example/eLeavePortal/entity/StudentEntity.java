package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentPersonalProfileEntity personalProfile;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentAcademicProfileEntity academicProfile;
}
