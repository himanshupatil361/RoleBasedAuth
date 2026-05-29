package com.springsecurity.RoleBasedAuthentication.Security;


import com.springsecurity.RoleBasedAuthentication.Entity.Users;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class UserPrinciples implements UserDetails {

    public Users users;


    //  create constructor
    public UserPrinciples(Users user){
        this.users = user;    // right user object  is received from MyUserDetailsService
    }

    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + users.getRole())
        );
    }


    @Override
    public @Nullable String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }


    // make all true here
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}