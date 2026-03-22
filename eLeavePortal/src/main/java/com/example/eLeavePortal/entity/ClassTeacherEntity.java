package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "class_teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassTeacherEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classTeacherId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "batch_branch_section_id", nullable = false, unique = true)
    private BatchBranchSectionEntity batchBranchSection;
}
