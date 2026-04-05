package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "hods")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "batch", nullable = false)
    private BatchEntity batch;

    @ManyToOne
    @JoinColumn(name = "branch", nullable = false)
    private BranchEntity branch;
}
