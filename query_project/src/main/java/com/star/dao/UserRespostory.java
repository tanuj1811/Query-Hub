package com.star.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.star.entity.User;

public interface UserRespostory extends JpaRepository<User, Integer> {
    @Query("Select u from User u where u.email = :email")
    public User getUserFromUserEmail(@Param("email") String email);
    
}