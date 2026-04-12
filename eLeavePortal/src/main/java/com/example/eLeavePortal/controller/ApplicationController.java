package com.example.eLeavePortal.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import com.example.eLeavePortal.dto.ApplicationDto;
import com.example.eLeavePortal.enums.ApplicationStatusEnums;
import com.example.eLeavePortal.service.ApplicationService;

import lombok.RequiredArgsConstructor;

// # Applications
// GET    /application                      // Application creation page
// POST   /application                      // New application
// GET    /application/{id}                 // Edit application page
// POST   /application/{id}                 // Update existing application
// GET    /application/all                  // List of applications the user permitted to see
// GET    /application/view/{id}            // View single application details
// GET    /application/student/{username}   // List of applications of a student
// PATCH  /application/{id}                 // Update application status
// DELETE /application/{id}                 // optional, maybe only admin can...

@Controller
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping
    public String application(Model model) {
        model.addAttribute("applicationDto", new ApplicationDto());
        return "pages/applicationUpdate";
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping
    public String save(@ModelAttribute("applicationDto") ApplicationDto dto,
            @RequestParam("action") String action, Authentication auth) {
        Long id = applicationService.saveApplication(auth.getName(), dto, action);
        return "redirect:/application/view/" + id;
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/{id}")
    public String edit(@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("applicationDto", applicationService.getApplication(id, auth.getName()));
        return "pages/applicationUpdate";
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("applicationDto") ApplicationDto dto,
            @RequestParam String action, Authentication auth) {
        dto.setId(id);
        applicationService.saveApplication(auth.getName(), dto, action);
        return "redirect:/application/view/" + id;
    }
    
    @GetMapping("/view/{id}")
    public String details(@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("applicationDto", applicationService.getApplication(id, auth.getName()));
        model.addAttribute("approvalHistory", applicationService.getApprovalHistory(id, auth.getName()));
        return "pages/applicationDetails";
    }

    @GetMapping("/all")
    public String getAll(Model model, Authentication auth){
        model.addAttribute("applications", applicationService.getPermittedApplications(auth.getName()));
        return "pages/applicationList";
    }

    @GetMapping("/student/{username}")
    public String getStudentAll(@PathVariable String username, Model model, Authentication auth) {
        model.addAttribute("applications", applicationService.getStudentApplications(username, auth.getName()));
        return "pages/applicationList";
    }

    @PatchMapping("/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam ApplicationStatusEnums status,
            @RequestParam(required = false) String remark, Authentication auth) {
        applicationService.updateStatus(id, status, remark, auth.getName());
        return "redirect:/application/view/" + id;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CT')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Authentication auth) {
        // applicationService.delete(id);
        // return "redirect:/application/all";
        return "redirect:/under-construction";
    }

    @PostMapping("/enhance")
    @ResponseBody
    @PreAuthorize("hasAuthority('STUDENT')")
    public Map<String, String> enhance(@RequestParam String reason, @RequestParam String explanation) {
        String enhanced = applicationService.enhanceText(reason, explanation);
        return Map.of("result", enhanced);
    }
}
