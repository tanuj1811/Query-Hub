package com.star.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "Comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int commentId;
    
    @Column(name = "comment_data")
    private String commentData;

    private int parentId;
    private String commentName;
    private String commentEmail;

    @CreatedDate
    private LocalDate createdAt;
   
    @ManyToOne
    private Question query;

    public int getCommentId() {
        return commentId;
    }



    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }



    public String getCommentData() {
        return commentData;
    }



    public void setCommentData(String commentData) {
        this.commentData = commentData;
    }



    public int getParentId() {
        return parentId;
    }



    public void setParentId(int parentId) {
        this.parentId = parentId;
    }



    public String getCommentEmail() {
        return commentEmail;
    }



    public void setCommentEmail(String commentEmail) {
        this.commentEmail = commentEmail;
    }



    public LocalDate getCreatedAt() {
        return createdAt;
    }



    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }



    public Question getQuery() {
        return query;
    }



    public void setQuery(Question query) {
        this.query = query;
    }


    public String getCommentName() {
        return commentName;
    }



    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }



    @Override
    public String toString() {
        return "Comment [commentData=" + commentData + ", commentEmail=" + commentEmail + ", commentId=" + commentId
                + ", commentName=" + commentName + ", createdAt=" + createdAt + ", parentId=" + parentId + ", query="
                + query + "]";
    }




    public Comment(int commentId, String commentName, String commentData, int parentId, String commentEmail,
            LocalDate createdAt, Question query) {
        this.commentId = commentId;
        this.commentName = commentName;
        this.commentData = commentData;
        this.parentId = parentId;
        this.commentEmail = commentEmail;
        this.createdAt = createdAt;
        this.query = query;
    }



    public Comment() {
    }



    
}
