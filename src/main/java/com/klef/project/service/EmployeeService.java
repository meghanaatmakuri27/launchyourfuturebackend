package com.klef.project.service;

import java.util.List;
import com.klef.project.model.Employee;

public interface EmployeeService {
    public String addEmployee(Employee employee);
    public String updateEmployee(Employee employee);
    public Employee viewEmployeeById(long id);
    public List<Employee> viewAllEmployees();
    public String deleteEmployee(long id);
    public Employee checkEmployeeLogin(String email, String password);
}
