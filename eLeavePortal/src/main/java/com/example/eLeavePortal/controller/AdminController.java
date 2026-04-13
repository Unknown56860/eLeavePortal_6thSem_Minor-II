package com.example.eLeavePortal.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eLeavePortal.dto.AuthUserDto;
import com.example.eLeavePortal.enums.RolesEnum;
import com.example.eLeavePortal.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // ---- Add ----
    @PostMapping("/batch")
    public void addBatch(@RequestParam String name) { adminService.addBatch(name); }

    @PostMapping("/branch")
    public void addBranch(@RequestParam String name) { adminService.addBranch(name); }

    @PostMapping("/section")
    public void addSection(@RequestParam String name) { adminService.addSection(name); }

    @PostMapping("/hostel")
    public void addHostel(@RequestParam String name) { adminService.addHostel(name); }

    // ---- Delete ----
    @PostMapping("/batch/delete")
    public void deleteBatch(@RequestParam Long id) { adminService.deleteBatch(id); }

    @PostMapping("/branch/delete")
    public void deleteBranch(@RequestParam Long id) { adminService.deleteBranch(id); }

    @PostMapping("/section/delete")
    public void deleteSection(@RequestParam Long id) { adminService.deleteSection(id); }

    @PostMapping("/hostel/delete")
    public void deleteHostel(@RequestParam Long id) { adminService.deleteHostel(id); }

    // ---- Admin ----
    @PostMapping("/admin/create")
    public void createAdmin(@ModelAttribute AuthUserDto dto) {
        dto.setRole(RolesEnum.ADMIN);
        adminService.createAdmin(dto);
    }

    @PostMapping("/admin/delete")
    public void deleteAdmin(@RequestParam Long id) {
        adminService.deleteAdmin(id);
    }
}
