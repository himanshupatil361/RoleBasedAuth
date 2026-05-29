package com.springsecurity.RoleBasedAuthentication.Service;



import com.springsecurity.RoleBasedAuthentication.Entity.Users;
import com.springsecurity.RoleBasedAuthentication.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // create Teacher n student
    public Users admin(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword())); //
        user.setRole("ADMIN");
        return  userRepo.save(user);
    }

}
