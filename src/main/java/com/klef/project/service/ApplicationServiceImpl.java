package com.klef.project.service;

import com.klef.project.model.Application;
import com.klef.project.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    @Override
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    @Override
    public Application saveApplication(Application application) {
        // Check if the email already exists for the same jobId
        if (isApplicationExist(application.getEmail(), application.getJobId())) {
            // You can either throw an exception or handle it according to your business logic
            throw new IllegalArgumentException("Application already exists for this job and email.");
        }
        
        // If it doesn't exist, save the application
        return applicationRepository.save(application);
    }
    public Application updateStatus(Long applicationId, String newStatus) {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found with ID " + applicationId));

        application.setStatus(newStatus);
        return applicationRepository.save(application);
    }

    @Override
    public boolean isApplicationExist(String email, Long jobId) {
        return applicationRepository.existsByEmailAndJobId(email, jobId);
    }
    public List<Application> getApplicationsByEmail(String email) {
        return applicationRepository.findByEmail(email); // Calls the repository to find applications by email
    }
}