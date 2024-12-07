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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB limit for file upload

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * POST endpoint to create a new application
     */
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

            // Handle file blob conversion safely
            if (resume != null && resume.getSize() <= MAX_FILE_SIZE) {
                Blob blob = new SerialBlob(resume.getBytes());
                application.setResume(blob);
            }

            application.setEducationDetails(educationDetails);
            application.setSkills(skills);

            applicationService.saveApplication(application);
            return ResponseEntity.ok("Application added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * GET endpoint to retrieve all applications
     */
    @GetMapping("/")
    public ResponseEntity<List<Application>> getAllApplications() {
        try {
            return ResponseEntity.ok(applicationService.getAllApplications());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET endpoint to retrieve a specific application by its ID
     */
    @GetMapping("/{applicationId}")
    public ResponseEntity<Application> getApplication(@PathVariable Long applicationId) {
        Optional<Application> application = applicationService.getApplicationById(applicationId);
        return application.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * GET endpoint to fetch applications by email
     */
    @GetMapping("/by-email/{email}")
    public ResponseEntity<List<Application>> getApplicationsByEmail(@PathVariable String email) {
        try {
            List<Application> applications = applicationService.getApplicationsByEmail(email);
            if (applications.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE endpoint to delete application by ID
     */
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
