package com.klef.project.controller;

import com.klef.project.model.Application;
import com.klef.project.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.rowset.serial.SerialBlob;

import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB limit

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/submitapplication")
    public ResponseEntity<String> submitApplication(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("tenthcgpa") double tenthCgpa,
            @RequestParam("twelethcgpa") double twelethCgpa,
            @RequestParam("gradutioncgpa") double gradutionCgpa,
            @RequestParam("resume") MultipartFile resumeFile) {
        try {
            if (resumeFile.isEmpty() || !resumeFile.getOriginalFilename().endsWith(".pdf")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid resume file. Please upload a PDF.");
            }
            Application application = new Application();
            application.setName(name);
            application.setEmail(email);
            application.setTenthcgpa(tenthCgpa);
            application.setTwelethcgpa(twelethCgpa);
            application.setGradutioncgpa(gradutionCgpa);
            application.setResume(new javax.sql.rowset.serial.SerialBlob(resumeFile.getBytes()));

            // Save application using the service
           applicationService.saveApplication(application);

            return ResponseEntity.status(HttpStatus.CREATED).body("submitted ");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing the resume file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error submitting application: " + e.getMessage());
        }
    }

    @GetMapping("/viewall")
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<Application> getApplication(@PathVariable Integer applicationId) {
        return applicationService.getApplicationById(applicationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Integer id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.ok("Deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Application not found");
        }
    }
}
