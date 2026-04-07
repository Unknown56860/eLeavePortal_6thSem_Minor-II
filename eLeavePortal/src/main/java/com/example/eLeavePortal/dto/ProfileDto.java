package com.example.eLeavePortal.dto;

import java.util.List;

import com.example.eLeavePortal.enums.ProfileStatusEnums;
import com.example.eLeavePortal.enums.RolesEnum;

import lombok.Data;

@Data
public class ProfileDto {

    private String username;
    private RolesEnum role;
    private ProfileStatusEnums status;

    // Contact
    private String name;
    private String mobile;
    private String whatsapp;
    private String email;
    private String pfpUrl;

    // Student Personal
    private String homeAddress;

    // Academic
    private String regNumber;
    private String rollNumber;
    private String collegeMail;

    private Long batchId;
    private Long branchId;
    private Long sectionId;
    private Long hostelId;

    private String hostelRoom;

    // Faculties
    private Long classTeacherId;
    private Long hodId;
    private Long wardenId;
    private Long hostelControllerId;

    // Hostels
    private List<Long> hostelIds;
    
}
