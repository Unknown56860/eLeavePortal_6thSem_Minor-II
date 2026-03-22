package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "batch_branches",
    uniqueConstraints = @UniqueConstraint(columnNames = {"batch_id", "branch_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchBranchEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchBranchId;

    @ManyToOne(optional = false)
    private BatchEntity batch;

    @ManyToOne(optional = false)
    private BranchEntity branch;
}
