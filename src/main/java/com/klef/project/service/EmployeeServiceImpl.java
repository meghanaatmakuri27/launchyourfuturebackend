package com.klef.project.service;

import com.klef.project.model.Employee;
import com.klef.project.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public String addEmployee(Employee employee) {
        try {
            employee.setId(null); // Ensure ID is null for new employees
            employeeRepository.save(employee);
            return "Employee added successfully with ID: " + employee.getId();
        } catch (Exception e) {
            return "Error adding employee: " + e.getMessage();
        }
    }

    @Override
    public String updateEmployee(Employee employee) {
        Optional<Employee> existingEmployeeOpt = employeeRepository.findById(employee.getId());
        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();
            existingEmployee.setName(employee.getName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhone(employee.getPhone());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setSalary(employee.getSalary());
            existingEmployee.setIsActive(employee.getIsActive());
            employeeRepository.save(existingEmployee);
            return "Employee updated successfully with ID: " + employee.getId();
        } else {
            return "Employee not found with ID: " + employee.getId();
        }
    }

    @Override
    public Employee viewEmployeeById(long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> viewAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public String deleteEmployee(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.deleteById(id);
            return "Employee deleted successfully with ID: " + id;
        } else {
            return "Employee not found with ID: " + id;
        }
    }
    @Override
    public Employee checkEmployeeLogin(String email, String password) {
        return employeeRepository.checkemployeelogin(email, password);
    }
}
