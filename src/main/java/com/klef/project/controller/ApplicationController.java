package com.klef.project.controller;

import com.klef.project.model.Application;
import com.klef.project.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.rowset.serial.SerialBlob;
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

    @PostMapping("/add")
    public ResponseEntity<String> addApplication(
            @RequestParam Long jobId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String gender,
            @RequestParam String country,
            @RequestParam Boolean canVerifyWork,
            @RequestParam(required = false) MultipartFile resume,
            @RequestParam(required = false) List<String> educationDetails,
            @RequestParam(required = false) List<String> skills
    ) {
        try {
            Application application = new Application();
            application.setJobId(jobId);
            application.setFirstName(firstName);
            application.setLastName(lastName);
            application.setEmail(email);
            application.setPhone(phone);
            application.setGender(gender);
            application.setCountry(country);
            application.setCanVerifyWork(canVerifyWork);

            if (resume != null && resume.getSize() <= MAX_FILE_SIZE) {
                byte[] resumeBytes = resume.getBytes();
                Blob blob = new SerialBlob(resumeBytes);
                application.setResume(blob);
            } else {
                application.setResume(null); // Handle when resume isn't uploaded.
            }
            

            // Serialize lists into comma-separated strings for database storage
            application.setEducationDetails(
                    educationDetails != null ? String.join(",", educationDetails) : ""
            );
            application.setSkills(
                    skills != null ? String.join(",", skills) : ""
            );

            // Persist the application entity
            applicationService.saveApplication(application);
            return ResponseEntity.ok("Application added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/viewall")
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<Application> getApplication(@PathVariable Long applicationId) {
        return applicationService.getApplicationById(applicationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.ok("Deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Application not found");
        }
    }
}
