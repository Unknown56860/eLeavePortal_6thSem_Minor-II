package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "batches", uniqueConstraints = {
    @UniqueConstraint(columnNames = "batch")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    @Column(nullable = false)
    private String batch;
}
