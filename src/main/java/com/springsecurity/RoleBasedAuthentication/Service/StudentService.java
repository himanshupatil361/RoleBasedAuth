package com.springsecurity.RoleBasedAuthentication.Service;



import com.springsecurity.RoleBasedAuthentication.Entity.Student;
import com.springsecurity.RoleBasedAuthentication.Entity.Users;
import com.springsecurity.RoleBasedAuthentication.Repository.StudentRepo;
import com.springsecurity.RoleBasedAuthentication.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {


    // Create student by teacher
    @Autowired
    private StudentRepo studentRepo;

    // BCrypt object
    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);

    public Student createStudent(Student student){
        // authentication user
        Users user = student.getUser();

        user.setPassword(encoder.encode(user.getPassword()));

        user.setRole("STUDENT");

        // save in users table
        Users savedUser = userRepo.save(user);

        // connect saved user with teacher
        student.setUser(savedUser);

        // save teacher table
        return studentRepo.save(student);

    }   // create


    // Get all
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    // get by id
    public Student getStudentbyId(Long id){
        return studentRepo.findById(id).orElseThrow(() ->
                new RuntimeException("student not found with id:" + id));
    }

    // post update
    public Student updateStudent(Long id, Student newstud){

        Student oldstud =studentRepo.findById(id).orElseThrow(()->
                new RuntimeException("Student not found: "+id));

        oldstud.setUsername(newstud.getUsername());
        oldstud.setCourse(newstud.getCourse());
        oldstud.setAge(newstud.getAge());

        // update users table also
        Users user = oldstud.getUser();
        if(user != null){

            user.setUsername(newstud.getUsername());

            // update password if sent
            if(newstud.getUser() != null &&
                    newstud.getUser().getPassword() != null){

                user.setPassword(
                        encoder.encode(newstud.getUser().getPassword())
                );
            }
            userRepo.save(user);
        }

        return  studentRepo.save(oldstud);
    }


    // 2 forEach suing switch case use this easy
    public Student patchStudent(Long id, Map<String, Object> updates) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Users users=student.getUser();

        updates.forEach((key, value) -> {
            switch (key) {
                case "username":
                    student.setUsername(value.toString());
                    break;

                case "course":
                    student.setCourse(value.toString());
                    break;

                case "age":
                    student.setAge(Integer.parseInt(value.toString()));
                    break;

                case "password": if(users != null){
                    users.setPassword(encoder.
                            encode(value.toString()));
                }
                // nested loop for username n passwod change of student
                case "user":
                    Map<String, Object> userUpdates =
                            (Map<String, Object>) value;

                    userUpdates.forEach((userKey, userValue) -> {

                        switch (userKey) {

                            case "username":
                                users.setUsername(
                                        userValue.toString()
                                );
                                break;

                            case "password":
                                users.setPassword(
                                        encoder.encode(
                                                userValue.toString()
                                        )
                                );
                                break;

                            default:
                                throw new RuntimeException(
                                        "Invalid user field"
                                );
                        }
                    });

                    break;
                default:
                    throw new RuntimeException("Invalid field: " + key);
            }
        });

        // save users table
        if(users != null){
            userRepo.save(users);
        }

        return studentRepo.save(student);
    }

    // delete
    public boolean deleteStudent(Long id){
        Optional<Student> optionalStudent = studentRepo.findById(id);

        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();

            Users user= student.getUser();
            // break relationship first
            if(user != null){
                user.setStudent(null);
            }
            student.setUser(null);

            // delete student first
            studentRepo.delete(student);

            // delete linked user
            if(user != null){
                userRepo.delete(user);
            }

            return true;
        }

        return false;
    }



    // STUDENT Pofile

    @Autowired
    private UserRepo userRepo;

    public Student getStudentProfile(String username) {

        Student student = studentRepo.findByUsername(username);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        return student;
    }

    // PATCH password only
// StudentController
    public Student patchforStudent(String username, Map<String, Object> updates) {
        Student student = studentRepo.findByUsername(username);
        if(student ==null){
            throw  new RuntimeException("Student not found");
        }
        Users users=student.getUser();

        updates.forEach((key, value) -> {
            switch (key) {

                case "password": if(users != null){
                    users.setPassword(encoder.
                            encode(value.toString()));
                }
                    break;

                default:
                    throw new RuntimeException("Invalid field: " + key);
            }
        });
        // save users table
        if(users != null){
            userRepo.save(users);
        }
        return studentRepo.save(student);
    }

}
