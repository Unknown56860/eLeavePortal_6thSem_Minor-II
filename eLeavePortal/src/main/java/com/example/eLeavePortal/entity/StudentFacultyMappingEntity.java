package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "student_faculty_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentFacultyMappingEntity {

    @Id
    private Long studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private StudentEntity student;
    
    @ManyToOne
    @JoinColumn(name = "ct_id", nullable = false)
    private ClassTeacherEntity classTeacher;

    @ManyToOne
    @JoinColumn(name = "hod_id", nullable = false)
    private HodEntity hod;

    @ManyToOne
    @JoinColumn(name = "warden_id", nullable = false)
    private WardenEntity warden;
    
    @ManyToOne
    @JoinColumn(name = "hc_id", nullable = false)
    private HostelControllerEntity hostelController;
}

