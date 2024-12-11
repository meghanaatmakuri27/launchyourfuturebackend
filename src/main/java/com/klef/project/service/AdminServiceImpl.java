package com.klef.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.project.model.Admin;
import com.klef.project.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Admin login
    @Override
    public Admin adminLogin(String username, String password) {
        // Assuming only one admin in the system
        Admin admin = adminRepository.findById(username).orElse(null);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    // Get admin details (since there's only one admin)
    @Override
    public Admin getAdmin() {
        return adminRepository.findById("admin@gmail.com").orElse(null); // Assuming "admin1" is the only admin
    }
}

