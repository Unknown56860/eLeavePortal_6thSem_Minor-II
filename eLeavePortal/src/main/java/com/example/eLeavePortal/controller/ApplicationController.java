package com.example.eLeavePortal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/application")
public class ApplicationController {
    // To control all endpoints related to aplications for all roles
    // It includes applications list view, single application view, application save and edit from student.

    @GetMapping("/all")
    public String getAllApplications(Model model){

        // studProfileImg, studentName, studentRoll, leaveReason, status, startDate, endDate
        List <Map<String, Object>> applications = new ArrayList<>();

        for(int i=0; i<10; i++){
            Map<String, Object> entry = new HashMap<>();
            entry.put("studentProfileImg", "/images/icons/profile-circle.svg");
            entry.put("studentName", "Abhinav Kumar");
            entry.put("studentRoll", "23CSEDS042");
            entry.put("leaveReason", "Holiday!");
            entry.put("status", "Drafted");
            entry.put("startDate", "01/01/2000");
            entry.put("endDate", "31/12/2000");

            applications.add(entry);
        }

        model.addAttribute("applications", applications);

        return "pages/applications";
    }
    
    // @GetMapping("/view")
    // @GetMapping("/save")
    // @GetMapping("/update")
    // @GetMapping("/delete")
}
