package com.epam.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/protected")
public class ProtectedZoneController {

    @GetMapping(value = "/add")
    public String addService(HttpServletRequest request) {
        return "Remote user: "+request.getRemoteUser();
    }

}
