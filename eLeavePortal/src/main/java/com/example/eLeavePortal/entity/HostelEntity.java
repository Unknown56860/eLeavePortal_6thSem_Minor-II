package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "hostels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hostelId;

    @Column(length = 150, nullable = false, unique = true)
    private String name;
}
