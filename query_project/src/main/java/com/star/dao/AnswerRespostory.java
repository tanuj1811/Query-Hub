package com.star.dao;

import java.util.List;

import com.star.entity.Answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * AnswerRespostory
 */
public interface AnswerRespostory extends JpaRepository<Answer,Integer> {

    @Query("from Answer as c where c.query.queryId = :queryId")
    public List<Answer> getAnswerByqueryId(@Param("queryId") Integer queryId);
    
}