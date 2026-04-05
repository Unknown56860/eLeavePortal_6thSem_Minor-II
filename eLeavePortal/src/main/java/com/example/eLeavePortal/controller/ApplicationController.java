package com.example.eLeavePortal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// # Applications
// POST   /application          // New application (or modify an existing for student only)
// GET    /application          // Application page
// GET    /application/all      // List of applications the user permitted to see
// GET    /application/{id}     // Single application details of other user or self
// PATCH  /application/{id}     // Update application of other user like status
// DELETE /application/{id}     // optional, maybe only admin can...

@Controller
@RequestMapping("/application")
public class ApplicationController {

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
}
