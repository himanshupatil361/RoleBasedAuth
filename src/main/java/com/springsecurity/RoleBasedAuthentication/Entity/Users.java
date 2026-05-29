package com.springsecurity.RoleBasedAuthentication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String role;

    // One user -> one teacher
    @OneToOne(mappedBy = "user")
    @JsonIgnore  // it will stop all from going ito loop{{{{}}}}}
    private Teacher teacher;

    // One user -> one student
    @OneToOne(mappedBy = "user")
    @JsonIgnore  // it will stop all from going ito loop{{{{}}}}}
    private Student student;

}
