package com.example.eLeavePortal.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/under-construction")
    public String undedrConstruction() {
        return "error/underConstruction";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/403";
    }

    @GetMapping("/not-found")
    public String notFound() {
        return "error/404";
    }

    @GetMapping("/server-error")
    public String serverError() {
        return "error/500";
    }
}
