package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "wardens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WardenEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wardenId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "hc_id", nullable = false)
    private HostelControllerEntity hostelController;

    @OneToOne(optional = false)
    @JoinColumn(name = "hostel_id", nullable = false, unique = true)
    private HostelEntity hostel;
}
