package com.tss.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Permission error controller(403)
 */
@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "permissionError";
    }
}
