package com.springsecurity.RoleBasedAuthentication.Repository;

import com.springsecurity.RoleBasedAuthentication.Entity.Student;
import com.springsecurity.RoleBasedAuthentication.Entity.Teacher;
import com.springsecurity.RoleBasedAuthentication.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepo extends JpaRepository<Teacher,Long> {

    // for admin for crud teacher
    Optional<Teacher> findById(Long id);

    // for logged in teacher profile
    Teacher findByUsername(String username);
}
