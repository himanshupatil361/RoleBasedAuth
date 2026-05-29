package com.springsecurity.RoleBasedAuthentication.Service;


import com.springsecurity.RoleBasedAuthentication.Entity.Teacher;
import com.springsecurity.RoleBasedAuthentication.Entity.Users;
import com.springsecurity.RoleBasedAuthentication.Repository.TeacherRepo;
import com.springsecurity.RoleBasedAuthentication.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TeacherService {


    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private UserRepo userRepo;

    // BCrypt object
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // strength


    // create Teacher
    public Teacher createTeach(Teacher teacher) {

        // authentication user table
        Users user = teacher.getUser();

        user.setPassword(encoder.encode(user.getPassword()));

        user.setRole("TEACHER");

        // save in users table
        Users savedUser = userRepo.save(user);

        // connect saved user with teacher table
        teacher.setUser(savedUser);

        // save teacher table
        return teacherRepo.save(teacher);
    }


    // GET ALL TEACHERS
    public List<Teacher> getAllTeachers() {
        return teacherRepo.findAll();
    }

    // GET TEACHER BY ID
    public Teacher getTeacherById(Long id) {
        return teacherRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found :"+id));
    }

    // post update  for admin
    public Teacher updateTeacher(Long id, Teacher newteacher){

        Teacher oldTeacher =teacherRepo.findById(id).orElseThrow(()->
                new RuntimeException("Teacher not found : "+id));

        oldTeacher.setUsername(newteacher.getUsername());
        oldTeacher.setSubject(newteacher.getSubject());

        // update users table also
        Users user = oldTeacher.getUser();

        if(user != null){

            user.setUsername(newteacher.getUsername());

            // update password if sent
            if(newteacher.getUser() != null &&
                    newteacher.getUser().getPassword() != null){

                user.setPassword(
                        encoder.encode(newteacher.getUser().getPassword())
                );
            }
            userRepo.save(user);
        }

        return  teacherRepo.save(oldTeacher);
    }

    public Teacher patchTeacher(Long id, Map<String, Object> updates) {
        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found " +id));

        Users users=teacher.getUser();

        updates.forEach((key, value) -> {
            switch (key) {

                case "subject": teacher.setSubject(value.toString());
                    break;

                case "username": teacher.setUsername(value.toString());
                    // update users table
                    if(users != null){
                        users.setUsername(value.toString());
                    }
                    break;

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

        return teacherRepo.save(teacher);
    }

    // DELETE TEACHER
    public boolean deleteTeacher(Long id){

        Optional<Teacher> optionalTeacher = teacherRepo.findById(id);

        if(optionalTeacher.isPresent()){

            Teacher teacher = optionalTeacher.get();

            Users user = teacher.getUser();

            // break relationship first
            if(user != null){
                user.setTeacher(null);
            }

            teacher.setUser(null);

            // delete teacher first
            teacherRepo.delete(teacher);

            // delete linked user
            if(user != null){
                userRepo.delete(user);
            }

            return true;
        }

        return false;
    }




    // for teacher controller
    // Get logged in teacher profile


    public Teacher getTeacherProfile(String username) {

        Teacher teacher = teacherRepo.findByUsername(username);

        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }
        return teacher;
    }

    public Teacher patchforTeacher(String username, Map<String, Object> updates) {
        Teacher teacher = teacherRepo.findByUsername(username);
        if(teacher ==null){
            throw  new RuntimeException("Teacher not found");
        }
        Users users=teacher.getUser();

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
        return teacherRepo.save(teacher);
    }

}
