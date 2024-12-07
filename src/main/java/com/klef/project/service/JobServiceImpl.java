package com.klef.project.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.project.model.Job;
import com.klef.project.repository.JobRepository;
import com.klef.project.service.JobService;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public String addjob(Job j) {
        try {
            jobRepository.save(j);
            return "Job added successfully with ID: " + j.getId();
        } catch (Exception e) {
            return "Error adding job: " + e.getMessage();
        }
    }

    @Override
    public String updatejob(Job j) {
        Optional<Job> existingJob = jobRepository.findById(j.getId());
        if (existingJob.isPresent()) {
            jobRepository.save(j);
            return "Job updated successfully with ID: " + j.getId();
        } else {
            return "Job not found with ID: " + j.getId();
        }
    }

    @Override
    public Job viewjobbyid(long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public List<Job> viewAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public String deleteJob(long id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isPresent()) {
            jobRepository.deleteById(id);
            return "Job deleted successfully with ID: " + id;
        } else {
            return "Job not found with ID: " + id;
        }
    }
}
