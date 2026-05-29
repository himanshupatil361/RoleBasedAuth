package com.springsecurity.RoleBasedAuthentication.Controller;

import com.springsecurity.RoleBasedAuthentication.Entity.Student;
import org.springframework.security.core.Authentication;
import com.springsecurity.RoleBasedAuthentication.Entity.Teacher;
import com.springsecurity.RoleBasedAuthentication.Service.StudentService;
import com.springsecurity.RoleBasedAuthentication.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;  // for get teacher by id (profilr)
                                            // patch teacher

    @Autowired
    private StudentService studentService; // for get all /one student by id
                                           // update student



    // Logged in teacher profile for teacher using teacherprofiledto

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {

        try {
            String username = authentication.getName();
            return ResponseEntity.ok(teacherService.getTeacherProfile(username));
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    // PATCH password only
// TeacherController

    @PatchMapping("/patchTeacher")
    public ResponseEntity<?> patchTeacher(
            Authentication authentication,
            @RequestBody Map<String, Object> updates) {

        try {
            String username = authentication.getName();
            Teacher updatedTeacher = teacherService.patchforTeacher(username, updates);
//            return ResponseEntity.ok(updatedTeacher); // for check
            return ResponseEntity.ok("Password updated Succesfully");
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // STUDENT CRUD

    // create
    @PostMapping("/addStudent")
    public Student creStud(@RequestBody Student student){
        return studentService.createStudent(student);
    }

    // Get all (list) users
    @GetMapping("/getStudents")
    public List<Student> getStud(){
        return studentService.getAllStudents();
    }


    // get by id
    @GetMapping("/getStudent/{id}")
    public ResponseEntity<?> getStudbyId(@PathVariable Long id){
        try{
            Student stu= studentService.getStudentbyId(id);
            return ResponseEntity.ok(stu);
        }
        catch (Exception e){
            return  ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // update
    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<?> updateStud(@PathVariable Long id,@RequestBody Student student){
        try{
            studentService.updateStudent(id,student);
            return ResponseEntity.ok("Student updated successfully");
        } catch (Exception e) {
            return  ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @PatchMapping("patchStudent/{id}")
    public ResponseEntity<?> updateStud(@PathVariable Long id,
                                        @RequestBody Map<String, Object> updates) {

        try {
            Student updatedStud = studentService.patchStudent(id, updates);
            return  ResponseEntity.ok("Student Updated Successfully");

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    // delete
    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<String> delStud(@PathVariable Long id){
        boolean del = studentService.deleteStudent(id);
        if (del){
            return ResponseEntity.ok("Student deleted Successfully...");
        }
        else{
            return ResponseEntity.status(404).body("Student not found: "+id );
        }
    }



}