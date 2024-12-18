package com.klef.project.service;

import com.klef.project.model.Application;
import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    Application saveApplication(Application application);
    List<Application> getAllApplications();
    Optional<Application> getApplicationById(Integer id);
    void deleteApplication(Integer id);
    public Application updateStatus(Integer applicationId, String newStatus) ;
    boolean isApplicationExist(String email, Integer jobId);  // Add this method to check for existing applications
    public List<Application> getApplicationsByEmail(String email);
}
