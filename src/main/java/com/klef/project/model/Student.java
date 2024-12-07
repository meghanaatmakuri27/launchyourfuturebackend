package com.klef.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_table")
public class Student {

    @Id
    @Column(name = "student_email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "student_name", nullable = true, length = 50)
    private String name;

    @Column(name = "student_password", nullable = false, length = 30)
    private String password;

    @Column(name = "student_graduation", nullable = true, length = 50)
    private int graduation;
    @Column(name = "student_department", nullable = true, length = 30)
    private String department;

    @Column(name = "student_contact", nullable = true, unique = true, length = 15)
    private String contact;

    @Column(name = "student_location", nullable = true, length = 50)
    private String location;


 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

  

    @Override
    public String toString() {
        return "Student [ name=" + name + ", email=" + email + ", password=" + password
                + ", department=" + department + ", contact=" + contact
                + ", location=" + location + ", ]";
    }

	public int getGraduation() {
		return graduation;
	}

	public void setGraduation(int graduation) {
		this.graduation = graduation;
	}
}
