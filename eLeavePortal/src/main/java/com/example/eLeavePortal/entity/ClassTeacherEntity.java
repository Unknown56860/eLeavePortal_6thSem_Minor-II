package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "class_teachers")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "batch", nullable = false)
    private BatchEntity batch;

    @ManyToOne
    @JoinColumn(name = "branch", nullable = false)
    private BranchEntity branch;

    @ManyToOne
    @JoinColumn(name = "section", nullable = false)
    private SectionEntity section;
}
