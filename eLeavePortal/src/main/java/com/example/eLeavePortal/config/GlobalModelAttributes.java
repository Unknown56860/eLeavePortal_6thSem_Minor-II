package com.example.eLeavePortal.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
class GlobalModelAttributes {
    
    @ModelAttribute
    public void addSessionAttribute(Model model, HttpSession session) {
        
        model.addAttribute("sessionUserName", session.getAttribute("userName"));
        model.addAttribute("sessionUserRole", session.getAttribute("userRole"));
    }
}