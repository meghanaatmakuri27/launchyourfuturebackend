package com.klef.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.project.model.Admin;
import com.klef.project.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
    @PostMapping("/login")
    public ResponseEntity<Object> adminLogin(@RequestBody Admin admin) {
        Admin authenticatedAdmin = adminService.adminLogin(admin.getUsername(), admin.getPassword());

        if (authenticatedAdmin != null) {
            return ResponseEntity.ok(new LoginResponse("Login successful", authenticatedAdmin));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials"));
        }
    }


    // View admin details (Since there's only one admin, it's a fixed username)
    @GetMapping("/view")
    public ResponseEntity<Admin> viewAdmin() {
        Admin admin = adminService.getAdmin();

        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    // Response model for success
    public static class LoginResponse {
        private String message;
        private Admin admin;

        public LoginResponse(String message, Admin admin) {
            this.message = message;
            this.admin = admin;
        }

        public String getMessage() {
            return message;
        }

        public Admin getAdmin() {
            return admin;
        }
    }

    // Response model for error
    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}

