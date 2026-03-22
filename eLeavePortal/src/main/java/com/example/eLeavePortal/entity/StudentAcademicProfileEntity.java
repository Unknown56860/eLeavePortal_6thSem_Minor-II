package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "student_academic_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAcademicProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentAcademicProfileId;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private StudentEntity student;

    @Column(nullable = false, unique = true)
    private String regNumber;

    @Column(nullable = false, unique = true)
    private String rollNumber;

    @Column(nullable = false, unique = true)
    private String collegeMail;

    @ManyToOne(optional = false)
    private BatchBranchSectionEntity batchBranchSection;

    @ManyToOne(optional = false)
    private HostelEntity hostel;

    @ManyToOne(optional = false)
    private HostelRoomsEntity hostelRoom;
}
