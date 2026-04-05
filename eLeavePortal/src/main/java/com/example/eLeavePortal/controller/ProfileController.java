package com.example.eLeavePortal.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.eLeavePortal.dto.ProfileDto;
import com.example.eLeavePortal.enums.ProfileStatusEnums;
import com.example.eLeavePortal.repository.*;
import com.example.eLeavePortal.service.ProfileService;

import lombok.RequiredArgsConstructor;

// # Profiles
// POST   /profile	        // New profile data, also any profile change data 
// GET    /profile	        // Profile update/edit page 
// GET    /profile/all      // List of profiles of other users the current user can see 
// GET    /profile/{id} 	// Single profile details of self or a different user 
// PATCH  /profile/{id} 	// Update an existng profile, done by other user like change profile status 
// DELETE /profile/{id}     // optional, maybe only classteacher or admin...

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
        return "redirect:/{auth.getName()}";
    }

    @GetMapping("/{username}")
    public String profileDetails(@PathVariable String username, Authentication auth, Model model) {
        addProfileAttributes(model, username);
        return "pages/profileDetails";
    }

    @PatchMapping("/{username}")
    public String updateStatus(@PathVariable String username, @RequestParam ProfileStatusEnums status) {
        profileService.updateStatus(username, status);
        return "redirect:/profile/{username}" + username;
    }

    public void addProfileAttributes(Model model, String username) {
        ProfileDto dto = profileService.getProfile(username);

        model.addAttribute("profile", dto);

        model.addAttribute("batches", batchRepository.findAll());
        model.addAttribute("branches", branchRepository.findAll());
        model.addAttribute("sections", sectionRepository.findAll());
        model.addAttribute("hostels", hostelRepository.findAll());

        model.addAttribute("classTeachers", classTeacherRepository.findAll());
        model.addAttribute("hods", hodRepository.findAll());
        model.addAttribute("wardens", wardenRepository.findAll());
        model.addAttribute("hostelControllers", hostelControllerRepository.findAll());
    }
}
