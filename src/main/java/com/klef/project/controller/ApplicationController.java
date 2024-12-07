package com.klef.project.controller;

import com.klef.project.model.Application;
import com.klef.project.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = "http://localhost:5173") // Ensure this matches your frontend's URL
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // Max file size limit of 10MB

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
    public ResponseEntity<String> addApplication(
            @RequestParam(value = "jobId") Long jobId,
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "gender") String gender,
            @RequestParam(value = "country") String country,
            @RequestParam(value = "canVerifyWork") String canVerifyWork,
            @RequestParam(value = "resume", required = false) MultipartFile resumeFile,
            @RequestParam(value = "educationDetails", required = false) List<String> educationDetails,
            @RequestParam(value = "skills", required = false) List<String> skills
    ) {
        try {
            // Validate required fields
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email is required.");
            }

            // Check for existing application
            if (applicationService.isApplicationExist(email, jobId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Application already exists for this job and email.");
            }

            // Handle file size validation
            if (resumeFile != null && !resumeFile.isEmpty()) {
                if (resumeFile.getSize() > MAX_FILE_SIZE) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Resume file size exceeds the maximum limit of 10MB.");
                }
            }

            // Prepare Application object
            Application application = new Application();
            application.setJobId(jobId);
            application.setFirstName(firstName);
            application.setLastName(lastName);
            application.setEmail(email);
            application.setPhone(phone);
            application.setGender(gender);
            application.setCountry(country);
            application.setCanVerifyWork(canVerifyWork != null ? Boolean.parseBoolean(canVerifyWork) : null);
            application.setResume(resumeFile != null && !resumeFile.isEmpty() ? resumeFile.getBytes() : null);
            application.setEducationDetails(educationDetails != null ? educationDetails : List.of());
            application.setSkills(skills != null ? skills : List.of());
            application.setStatus("UnderConsideration");

            // Save the application
            applicationService.saveApplication(application);
            return ResponseEntity.ok("Application added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<Application> getApplication(@PathVariable Long applicationId) {
        return applicationService.getApplicationById(applicationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/")
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/update/{applicationId}")
    public ResponseEntity<String> updateApplication(
            @PathVariable Long applicationId,
            @RequestBody Application updatedApplication,
            @RequestParam(value = "resume", required = false) MultipartFile resumeFile
    ) {
        return applicationService.getApplicationById(applicationId)
                .map(existingApplication -> {
                    mergeApplications(existingApplication, updatedApplication);
                    
                    if (resumeFile != null && !resumeFile.isEmpty()) {
                        try {
                            // Check file size for update
                            if (resumeFile.getSize() > MAX_FILE_SIZE) {
                                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body("Resume file size exceeds the maximum limit of 10MB.");
                            }
                            existingApplication.setResume(resumeFile.getBytes());
                        } catch (Exception e) {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid resume file.");
                        }
                    }
                    applicationService.saveApplication(existingApplication);
                    return ResponseEntity.ok("Application updated successfully.");
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found."));
    }

    private void mergeApplications(Application existing, Application updated) {
        if (updated.getFirstName() != null) existing.setFirstName(updated.getFirstName());
        if (updated.getLastName() != null) existing.setLastName(updated.getLastName());
        if (updated.getEmail() != null) existing.setEmail(updated.getEmail());
        if (updated.getPhone() != null) existing.setPhone(updated.getPhone());
        if (updated.getGender() != null) existing.setGender(updated.getGender());
        if (updated.getCountry() != null) existing.setCountry(updated.getCountry());
        if (updated.getCanVerifyWork() != null) existing.setCanVerifyWork(updated.getCanVerifyWork());
        if (updated.getEducationDetails() != null) existing.setEducationDetails(updated.getEducationDetails());
        if (updated.getSkills() != null) existing.setSkills(updated.getSkills());
    }

    @DeleteMapping("/delete/{applicationId}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long applicationId) {
        if (applicationService.getApplicationById(applicationId).isPresent()) {
            applicationService.deleteApplication(applicationId);
            return ResponseEntity.ok("Application deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStatus(
        @PathVariable("id") Long applicationId, 
        @RequestBody String newStatus) {

        // Call the service to update the status
        applicationService.updateStatus(applicationId, newStatus);

        // Return a response indicating success
        return ResponseEntity.ok("Status updated successfully");
    }
    @GetMapping("/by-email/{email}")
    public ResponseEntity<List<Application>> getApplicationsByEmail(@PathVariable String email) {
        List<Application> applications = applicationService.getApplicationsByEmail(email);
        
        if (applications.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns HTTP 204 if no applications found
        }
        
        return ResponseEntity.ok(applications); // Returns HTTP 200 with the list of applications
    }
}
