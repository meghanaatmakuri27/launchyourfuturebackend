package com.klef.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.project.model.Student;
import com.klef.project.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    
    

    @Override
    public String addstudent(Student s) {
        studentRepository.save(s);
      //  emailService.sendEmail(s.getEmail(),"student registered successfully","Welcome to Launch Your Future");
        return "Student Added Successfully";
    }

    @Override
    public String updateStudent(Student s) {
        Optional<Student> optionalStudent = studentRepository.findById(s.getEmail());
        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();

            existingStudent.setName(s.getName());
            existingStudent.setPassword(s.getPassword());
            existingStudent.setGraduation(s.getGraduation());
            existingStudent.setDepartment(s.getDepartment());
            existingStudent.setContact(s.getContact());
            existingStudent.setLocation(s.getLocation());

            studentRepository.save(existingStudent);
            return "Student Updated Successfully";
        } else {
            return "Student Not Found";
        }
    }

    @Override
    public Student viewstudentbyid(String email) {
        Optional<Student> obj = studentRepository.findById(email);
        return obj.orElse(null);
    }

    public Student checkstudentlogin(String email, String password) {
    	 return studentRepository.checkstudentlogin(email, password);
    }

    @Override
    public List<Student> viewAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public String deleteStudent(String email) {
        Optional<Student> optionalStudent = studentRepository.findById(email);
        if (optionalStudent.isPresent()) {
            studentRepository.deleteById(email);
            return "Student Deleted Successfully";
        } else {
            return "Student Not Found";
        }
    }
}
