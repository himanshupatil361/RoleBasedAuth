package com.springsecurity.RoleBasedAuthentication.Service;


import com.springsecurity.RoleBasedAuthentication.Entity.Student;
import com.springsecurity.RoleBasedAuthentication.Entity.Users;
import com.springsecurity.RoleBasedAuthentication.Repository.StudentRepo;
import com.springsecurity.RoleBasedAuthentication.Repository.UserRepo;
import com.springsecurity.RoleBasedAuthentication.Security.UserPrinciples;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // write this
    Users user = repo.findByUsername(username);  // repository custom method call

        if(user == null){
        System.out.println("User Not Found");
        throw new UsernameNotFoundException("User Not Found");
    }

        return new UserPrinciples(user); // step 4 return obj to security princile layer
}


}
