package com.star.controller;

import java.security.Principal;
import java.util.List;


import com.star.dao.QueryRespostory;
import com.star.dao.UserRespostory;
import com.star.entity.Question;
import com.star.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Search_Contoller {

    @Autowired
    private QueryRespostory queryRespostory;
    @Autowired
    private UserRespostory userRespostory;
  
    
    /**search handler */
    @GetMapping("/search/{title}")
    public ResponseEntity<?> search(@PathVariable String title,Principal principal){

        List<Question> searchedQueries = this.queryRespostory.findByTitleContaining(title);
        // if(searchedQueries.isEmpty()){
        //     System.out.println("empty list");
        // } else{
        //     searchedQueries.forEach(e -> {
        //         System.out.println("ys i m in");
        //         System.out.println(e.getTitle());
        //     });
        // }
        List<User> allUser = this.userRespostory.findAll();

        allUser.forEach(e -> {
            System.out.println(e.getUserId());
        });
        // List<Question> searchedQueriesfromAllUser = this.queryRespostory.findAllByTitleContaining("i am", allUser);
        // if(searchedQueriesfromAllUser.isEmpty()){
        //     System.out.println("empty list");
        // } else{
        //     searchedQueriesfromAllUser.forEach(e -> {
        //         System.out.println("ys i m in");
        //         System.out.println(e.getTitle());
        //     });
        // }

        
        return ResponseEntity.ok(searchedQueries);
    }
}
