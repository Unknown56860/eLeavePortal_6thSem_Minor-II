package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "student_academic_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAcademicProfileEntity {

    @Id
    private Long studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @Column(nullable = false, unique = true)
    private String regNumber;

    @Column(nullable = false, unique = true)
    private String rollNumber;

    @Column(nullable = false, unique = true)
    private String collegeMail;

    @ManyToOne
    @JoinColumn(name = "batch", nullable = false)
    private BatchEntity batch;

    @ManyToOne
    @JoinColumn(name = "branch", nullable = false)
    private BranchEntity branch;

    @ManyToOne
    @JoinColumn(name = "section", nullable = false)
    private SectionEntity section;

    @ManyToOne(optional = false)
    private HostelEntity hostel;

    @Column(nullable = false)
    private String hostelRoom;
}
