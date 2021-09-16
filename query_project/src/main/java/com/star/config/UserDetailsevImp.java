package com.star.config;

import com.star.dao.UserRespostory;
import com.star.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsevImp implements UserDetailsService {

    @Autowired
    private UserRespostory userRespostory;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        //fetching data from database
        User user = userRespostory.getUserFromUserEmail(email);
        if(user == null)
            throw new UsernameNotFoundException("Couldn't found User");

        UserDetail userDetail = new UserDetail(user);

        return userDetail;
    }
    
}
