package com.klef.project.model;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private double tenthcgpa;

    @Column(nullable = false)
    private double twelethcgpa;

    @Column(nullable = false)
    private double gradutioncgpa;

    @Column(nullable = false)
    private Blob resume;

    // Default Constructor
    public Application() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getTenthcgpa() {
        return tenthcgpa;
    }

    public void setTenthcgpa(double tenthcgpa) {
        this.tenthcgpa = tenthcgpa;
    }

    public double getTwelethcgpa() {
        return twelethcgpa;
    }

    public void setTwelethcgpa(double twelethcgpa) {
        this.twelethcgpa = twelethcgpa;
    }

    public double getGradutioncgpa() {
        return gradutioncgpa;
    }

    public void setGradutioncgpa(double gradutioncgpa) {
        this.gradutioncgpa = gradutioncgpa;
    }

    public Blob getResume() {
        return resume;
    }

    public void setResume(Blob resume) {
        this.resume = resume;
    }
}
