package com.klef.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.klef.project.model.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
	 @Query("select s from Student s where s.email=?1 and s.password=?2 ")
	  public Student checkstudentlogin(String email,String pwd);
}
