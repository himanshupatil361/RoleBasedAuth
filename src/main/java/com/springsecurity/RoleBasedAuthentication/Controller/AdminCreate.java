package com.springsecurity.RoleBasedAuthentication.Controller;

import com.springsecurity.RoleBasedAuthentication.Entity.Users;
import com.springsecurity.RoleBasedAuthentication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
//@RequestMapping("/admins")
public class AdminCreate {

    @Autowired
    private UserService userService;

// add main admin manually then if want to create another admin useit


    @PostMapping("/create-admin")
    public Users createAdmin(@RequestBody Users user) {
        return userService.admin(user);
    }
}
