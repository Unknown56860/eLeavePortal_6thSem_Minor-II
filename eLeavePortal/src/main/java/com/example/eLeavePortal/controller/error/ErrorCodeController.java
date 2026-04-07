package com.example.eLeavePortal.controller.error;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorCodeController implements ErrorController {
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        int statusCode = status!=null ? Integer.parseInt(status.toString()) : -1;

        switch(statusCode) {
            case 403: return "error/403";
            case 404: return "error/404";
            case 500: return "error/500";
        }

        return "error/error";
    }
}
