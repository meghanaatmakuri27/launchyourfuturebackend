package com.klef.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.klef.project.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
    
}
