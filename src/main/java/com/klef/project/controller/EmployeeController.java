package com.klef.project.controller;

import com.klef.project.controller.AdminController.LoginResponse;
import com.klef.project.model.Employee;
import com.klef.project.service.EmployeeService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        if (employee.getName() == null || employee.getEmail() == null || employee.getPhone() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Employee name, email, and phone are required.");
        }
        String result = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
        if (id != employee.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Employee ID in the URL does not match the employee ID in the request body.");
        }
        String result = employeeService.updateEmployee(employee);
        if (result.contains("successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Employee> viewEmployeeById(@PathVariable("id") long id) {
        Employee employee = employeeService.viewEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/viewall")
    public ResponseEntity<List<Employee>> viewAllEmployees() {
        List<Employee> employees = employeeService.viewAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {
        String result = employeeService.deleteEmployee(id);
        if (result.contains("successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/checkemployeelogin")
    public ResponseEntity<?> checkEmployeeLogin(@RequestBody Employee loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Employee employee = employeeService.checkEmployeeLogin(email, password); // Use an appropriate service method
        if (employee != null) {
            // Generate JWT Token
            String token = Jwts.builder()
                    .setSubject(email)
                    .claim("role", "employee") // Optional: Add custom claims
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token valid for 1 day
                    .signWith(SignatureAlgorithm.HS256, "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437") // Use a strong, secure key
                    .compact();

            // Respond with token and basic employee details
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
