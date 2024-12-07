package com.klef.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klef.project.model.Job;


public interface JobRepository extends JpaRepository<Job, Long>{

}
