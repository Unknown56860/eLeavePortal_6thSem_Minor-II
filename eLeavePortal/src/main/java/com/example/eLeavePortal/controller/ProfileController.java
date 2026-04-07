package com.example.eLeavePortal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import com.example.eLeavePortal.dto.ProfileDto;
import com.example.eLeavePortal.enums.ProfileStatusEnums;
import com.example.eLeavePortal.repository.*;
import com.example.eLeavePortal.service.ProfileService;

import lombok.RequiredArgsConstructor;

// # Profiles
// POST   /profile	                // add or update profile details 
// GET    /profile	                // Profile edit page
// GET    /profile/all/staff        // List of profiles of all faculties, any logged in user can see 
// GET    /profile/all/student      // List of profiles of all students the current user(Students excluded) should to see 
// GET    /profile/{username} 	    // Single profile details of any user (a student cannot see other student's profile)
// PATCH  /profile/{username} 	    // Update profile status (Admins and faculties only)
// DELETE /profile/{username}       // optional, maybe only classteacher or admin...

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    private final BatchRepository batchRepository;
    private final BranchRepository branchRepository;
    private final SectionRepository sectionRepository;
    private final HostelRepository hostelRepository;

    private final ClassTeacherRepository classTeacherRepository;
    private final HodRepository hodRepository;
    private final WardenRepository wardenRepository;
    private final HostelControllerRepository hostelControllerRepository;

    @GetMapping
    public String profile(Model model, Authentication auth) {
        addProfileAttributes(model, auth.getName());
        return "pages/profileUpdate";
    }

    @PostMapping
    public String save(@ModelAttribute("profile") ProfileDto dto, Authentication auth) {
        profileService.saveProfile(auth.getName(), dto);
        return "redirect:/" + auth.getName();
    }

    @GetMapping("/{username}")
    public String details(@PathVariable String username, Authentication auth, Model model) {
        addProfileAttributes(model, username);
        return "pages/profileDetails";
    }

    @PatchMapping("/{username}")
    public String updateStatus(@PathVariable String username, @RequestParam ProfileStatusEnums status) {
        profileService.updateProfileStatus(username, status);
        return "redirect:/profile/" + username;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CT')")
    @DeleteMapping("/{username}")
    public String delete(@PathVariable String username, Authentication auth) {
        return "redirect:/under-construction";
    }

    @GetMapping("/all/staff")
    public String allFaculty(Authentication auth, Model model) {
        model.addAttribute("profiles",
                profileService.getAllFaculty(auth.getName()));
        addChargesLookup(model);
        return "pages/profileList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CT', 'HOD', 'WARDEN', 'HOSTEL_CONTROLLER')")
    @GetMapping("/all/students")
    public String allStudents(Authentication auth, Model model) {
        model.addAttribute("profiles",
                profileService.getAllStudents(auth.getName()));
        addChargesLookup(model);
        return "pages/profileList";
    }


    private void addProfileAttributes(Model model, String username) {
        model.addAttribute("profile", 
                profileService.getProfile(username));
        addChargesLookup(model);
        addFacultiesLookup(model);
    }

    private void addChargesLookup(Model model) {
        model.addAttribute("batches", batchRepository.findAll());
        model.addAttribute("branches", branchRepository.findAll());
        model.addAttribute("sections", sectionRepository.findAll());
        model.addAttribute("hostels", hostelRepository.findAll());
    }

    private void addFacultiesLookup(Model model) {
        model.addAttribute("classTeachers", classTeacherRepository.findAll());
        model.addAttribute("hods", hodRepository.findAll());
        model.addAttribute("wardens", wardenRepository.findAll());
        model.addAttribute("hostelControllers", hostelControllerRepository.findAll());
    }
}
