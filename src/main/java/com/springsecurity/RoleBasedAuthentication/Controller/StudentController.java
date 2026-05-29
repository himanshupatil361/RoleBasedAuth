package com.springsecurity.RoleBasedAuthentication.Controller;


import com.springsecurity.RoleBasedAuthentication.Entity.Student;

import com.springsecurity.RoleBasedAuthentication.Entity.Teacher;
import com.springsecurity.RoleBasedAuthentication.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {

        try {
            String username = authentication.getName();
            Student student = studentService.getStudentProfile(username);
            return ResponseEntity.ok(student);

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PatchMapping("/patchStudent")
    public ResponseEntity<?> patchStudent(
            Authentication authentication,
            @RequestBody Map<String, Object> updates) {

        try {
            String username = authentication.getName();
            Student updatedStudent = studentService.patchforStudent(username, updates);
//            return ResponseEntity.ok(updatedTeacher); // for check
            return ResponseEntity.ok("Password updated Succesfully");
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}