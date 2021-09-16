package com.star.dao;

import java.util.List;

import com.star.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRespostory extends JpaRepository<Comment, Integer> {
   
   
    @Query("from Comment as c where c.query.queryId = :queryId")
    public List<Comment> getCommentsByqueryId(@Param("queryId") Integer queryId);
}
