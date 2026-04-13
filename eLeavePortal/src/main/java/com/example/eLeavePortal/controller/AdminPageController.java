package com.example.eLeavePortal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.eLeavePortal.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminPageController {
    private final AdminService adminService;

    @GetMapping("/site")
    public String sitePage(Model model) {
        model.addAttribute("dashboard", adminService.getDashboard());
        model.addAttribute("trend", adminService.getApplicationTrend());

        model.addAttribute("batches", adminService.getBatches());
        model.addAttribute("branches", adminService.getBranches());
        model.addAttribute("sections", adminService.getSections());
        model.addAttribute("hostels", adminService.getHostels());

        model.addAttribute("admins", adminService.getAdmins());

        return "pages/adminDashboard";
    }
}