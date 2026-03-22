package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "hods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HodEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hodId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @OneToOne(optional = false)
    private BatchBranchEntity batchBranch;
}
