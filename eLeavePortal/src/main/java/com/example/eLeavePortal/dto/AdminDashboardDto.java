package com.example.eLeavePortal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardDto {
    private long totalStudents;
    private long totalCT;
    private long totalHOD;
    private long totalWarden;
    private long totalHC;
    private long totalApplications;
}