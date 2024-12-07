package com.klef.project.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
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

    private void sendWelcomeEmail(String toEmail, String studentName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to Our Platform");
        message.setText("Dear " + studentName + ",\n\n" +
                "Welcome! Your student account has been successfully created.\n" +
                "Feel free to explore the platform and reach out if you have any questions.\n\n" +
                "Best regards,\nUniversity Team");
       // mailSender.send(message);
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
