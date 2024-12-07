package com.klef.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "applications", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "job_id"}))
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Job ID cannot be null")
    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @NotNull(message = "First name is required")
    @Size(max = 100, message = "First name must be less than 100 characters")
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(max = 100, message = "Last name must be less than 100 characters")
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotNull(message = "Phone number is required")
    @Size(max = 15, message = "Phone number must be less than 15 characters")
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false)
    private String email;

    @Lob
    @NotNull(message = "Resume cannot be null")
    @Column(name = "resume", nullable = false)
    private byte[] resume;

    @NotNull(message = "Gender is required")
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull(message = "Country is required")
    @Size(max = 100, message = "Country must be less than 100 characters")
    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @NotNull(message = "Can verify work cannot be null")
    @Column(name = "can_verify_work", nullable = false)
    private Boolean canVerifyWork; // Changed to Boolean for clarity and consistency

    @Column(name = "applied_at", nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @ElementCollection
    @CollectionTable(name = "education_details", joinColumns = @JoinColumn(name = "application_id"))
    @Column(name = "education_detail")
    private List<String> educationDetails;

    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(name = "application_id"))
    @Column(name = "skill")
    private List<String> skills;

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE"; // Default to "PENDING" status
    
    @PrePersist
    public void prePersist() {
        appliedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public byte[] getResume() { return resume; }
    public void setResume(byte[] resume) { this.resume = resume; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Boolean getCanVerifyWork() { return canVerifyWork; }
    public void setCanVerifyWork(Boolean canVerifyWork) { this.canVerifyWork = canVerifyWork; }

    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }

    public List<String> getEducationDetails() { return educationDetails; }
    public void setEducationDetails(List<String> educationDetails) { this.educationDetails = educationDetails; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
