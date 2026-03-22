package com.example.eLeavePortal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "hostel_rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostelRoomsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hostel_id")
    private HostelEntity hostel;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;
}
