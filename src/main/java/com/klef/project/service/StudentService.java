package com.klef.project.service;

import java.util.List;

import com.klef.project.model.Student;

public interface StudentService {
	public String addstudent(Student s);
	  public String updateStudent(Student s);
	  public Student viewstudentbyid(String email);
	  public Student checkstudentlogin(String email,String pwd);
	  public List<Student> viewAllStudents();
	  public String deleteStudent(String email);
}
