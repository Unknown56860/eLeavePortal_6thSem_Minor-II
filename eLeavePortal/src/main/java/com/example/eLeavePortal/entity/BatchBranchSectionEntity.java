package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "sections",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"batch_branch_id", "section_name"}
    )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchBranchSectionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchBranchSectionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "batch_branch_id")
    private BatchBranchEntity batchBranch;

    @Column(nullable = false)
    private String section;
}
