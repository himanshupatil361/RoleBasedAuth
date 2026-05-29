package com.springsecurity.RoleBasedAuthentication.Repository;

import com.springsecurity.RoleBasedAuthentication.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Long> {

    // for admin for crud student
    Optional<Student> findById(Long id);

    // for logged in student profile
    Student findByUsername(String username);
}
