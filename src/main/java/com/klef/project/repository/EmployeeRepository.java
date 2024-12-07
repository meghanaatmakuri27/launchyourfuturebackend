package com.klef.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.klef.project.model.Employee;
import com.klef.project.model.Student;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	@Query("select e from Employee e where e.email=?1 and e.password=?2 ")
	  public Employee checkemployeelogin(String email,String pwd);
}

