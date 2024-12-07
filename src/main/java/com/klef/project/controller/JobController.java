package com.klef.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.project.model.Job;
import com.klef.project.service.JobService;

@RestController
@RequestMapping("/job")
@CrossOrigin
public class JobController {

    @Autowired
    private JobService jobService;

    // Add a new Job
    @CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
    @PostMapping("/add")
    public ResponseEntity<String> addJob(@RequestBody Job job) {
        if (job.getTitle() == null || job.getCompany() == null || job.getLocation() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Job title, company, and location are required.");
        }
        String result = jobService.addjob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

 // Update a Job
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateJob(@PathVariable("id") long id, @RequestBody Job job) {
        // Ensure that the job ID matches the ID in the request
        if (id != job.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Job ID in the URL does not match the job ID in the request body.");
        }
        
        String result = jobService.updatejob(job);
        if (result.contains("successfully")) {
            return ResponseEntity.ok("Job updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found.");
        }
    }


    // View a Job by ID
    @GetMapping("/view/{id}")
    public ResponseEntity<Job> viewJobById(@PathVariable("id") long id) {
        Job job = jobService.viewjobbyid(id);
        if (job != null) {
            return ResponseEntity.ok(job);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    // View all Jobs
    @GetMapping("/viewall")
    public ResponseEntity<List<Job>> viewAllJobs() {
        List<Job> jobs = jobService.viewAllJobs();
        return ResponseEntity.ok(jobs);
    }

    // Delete a Job by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable("id") long id) {
        String result = jobService.deleteJob(id);
        if (result.contains("successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }
}
