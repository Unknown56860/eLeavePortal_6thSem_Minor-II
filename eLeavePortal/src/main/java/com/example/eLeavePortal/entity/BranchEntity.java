package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "branches", uniqueConstraints = {
    @UniqueConstraint(columnNames = "branch")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @Column(nullable = false)
    private String branch;
}
