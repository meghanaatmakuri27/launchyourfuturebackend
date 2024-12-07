package com.klef.project.model;



import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import java.sql.Blob;
import java.util.List;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    private Long jobId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private String country;
    private Boolean canVerifyWork;

   
    private Blob resume;

    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(name = "application_id"))
    @Column(name = "skill_name")
    private List<String> skills;

    @ElementCollection
    @CollectionTable(name = "education_details", joinColumns = @JoinColumn(name = "application_id"))
    @Column(name = "education_detail")
    private List<String> educationDetails;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getCanVerifyWork() {
        return canVerifyWork;
    }

    public void setCanVerifyWork(Boolean canVerifyWork) {
        this.canVerifyWork = canVerifyWork;
    }

    public Blob getResume() {
        return resume;
    }

    public void setResume(Blob resume) {
        this.resume = resume;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getEducationDetails() {
        return educationDetails;
    }

    public void setEducationDetails(List<String> educationDetails) {
        this.educationDetails = educationDetails;
    }
}
