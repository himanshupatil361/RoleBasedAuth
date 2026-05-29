package com.springsecurity.RoleBasedAuthentication.Controller;


import com.springsecurity.RoleBasedAuthentication.Entity.Student;
import com.springsecurity.RoleBasedAuthentication.Entity.Teacher;

import com.springsecurity.RoleBasedAuthentication.Service.StudentService;
import com.springsecurity.RoleBasedAuthentication.Service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    // CRUD Teacher

    @Autowired
    private TeacherService teacherService;

    // create Teacher n students
    @PostMapping("/createTeacher")
    public Teacher createTeachr(@RequestBody Teacher teacher){
        return teacherService.createTeach(teacher);
    }


    // Get all (list) users
    @GetMapping("/getTeachers")
    public List<Teacher> getAllTeachers(){
        return teacherService.getAllTeachers();
    }


    // get by id
    @GetMapping("/getTeacher/{id}")
    public ResponseEntity<?> getTeachersbyId(@PathVariable Long id){
        try{
            Teacher teacher= teacherService.getTeacherById(id);
            return ResponseEntity.ok(teacher);
        }
        catch (Exception e){
            return  ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // update
    @PutMapping("/updateTeacher/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id,@RequestBody Teacher teacher){
        try{
            teacherService.updateTeacher(id,teacher);
            return ResponseEntity.ok("Teacher updated successfully");
        } catch (Exception e) {
            return  ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @PatchMapping("/patchTeacher/{id}")
    public ResponseEntity<?> updateTeachr(@PathVariable Long id,
                                        @RequestBody Map<String, Object> updates) {

        try {
            Teacher updateTeacher = teacherService.patchTeacher(id, updates);

            return  ResponseEntity.ok("Teacher Updated Successfully");

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    // delete
    @DeleteMapping("/deleteTeacher/{id}")
    public ResponseEntity<String> delTeach(@PathVariable Long id){
        boolean del = teacherService.deleteTeacher(id);
        if (del){
            return ResponseEntity.ok("Teacher deleted Successfully...");
        }
        else{
            return ResponseEntity.status(404).body("Teacher not found: "+id );
        }
    }


    // Admin  CRUD Student

    @Autowired
    private StudentService studentService;

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


    @PatchMapping("/patchStudent/{id}")
    public ResponseEntity<?> updateStud(@PathVariable Long id,
                                        @RequestBody Map<String, Object> updates) {

        try {
            Student updatedStud = studentService.patchStudent(id, updates);
            return  ResponseEntity.ok("Data Updated Successfully");

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
