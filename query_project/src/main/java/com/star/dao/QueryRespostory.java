package com.star.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.star.entity.Question;
import com.star.entity.User;
public interface QueryRespostory extends JpaRepository<Question, Integer> {
   
    //pagination

    
    // currentPage - page 
    // query per paga - 10
    @Query("from Question as c where c.user.userId = :userId")
    public Page<Question> getQueriesByUserId(@Param("userId") Integer userId, Pageable pageable);

    public Page<Question> findAll(Pageable pageable);


    public List<Question> findByTitleContaining(String title);

    public List<Question> findAllByUser(User user);
   
    @Query("from Question as n where n.user.name = :name")
    public List<Question> findByNameContaining(String name);

    public List<Question> findByEmailContaining(String email);

}