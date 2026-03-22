package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "user_contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDetailId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 15)
    private String mobile;

    @Column(unique = true, length = 15)
    private String whatsapp;

    @Column(nullable = false, unique = true)
    private String email;
}
