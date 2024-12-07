package com.klef.project.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.project.model.Student;
import com.klef.project.service.StudentService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;
    @PostMapping("/addstudent")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        if (student.getEmail() == null || student.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Email and password are required.");
        }

        studentService.addstudent(student);
        //sendWelcomeEmail(student.getEmail(), "student");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Student created successfully");
    }

    

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/checkstudentlogin")
    public ResponseEntity<?> checkStudentLogin(@RequestBody Student loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Student student = studentService.checkstudentlogin(email, password);
        if (student != null) {
            // Generate JWT
            String token = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                    .signWith(SignatureAlgorithm.HS256, "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437") // Use a strong key in production
                    .compact();

            return ResponseEntity.ok(token); // Return the token
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    // View Student by Email (Primary Key)
    @GetMapping("/viewbyemail")
    public ResponseEntity<Student> viewByEmail(@RequestParam String email) {
        Student student = studentService.viewstudentbyid(email);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
        }
    }

    // Update Student
    @PutMapping("/updatestudent")
    public ResponseEntity<String> updateStudent(@RequestBody Student student) {
        String result = studentService.updateStudent(student);
        return ResponseEntity.ok(result);
    }

    // Delete Student
    @DeleteMapping("/deletestudent")
    public ResponseEntity<String> deleteStudent(@RequestParam String email) {
        String result = studentService.deleteStudent(email);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Student not found.");
        }
    }

    // Get All Students
    @GetMapping("/view")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.viewAllStudents();
        return ResponseEntity.ok(students);
    }
}
