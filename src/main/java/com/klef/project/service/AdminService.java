package com.klef.project.service;

import com.klef.project.model.Admin;

public interface AdminService {
    Admin adminLogin(String username, String password);
    Admin getAdmin();
}