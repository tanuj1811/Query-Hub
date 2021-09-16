package com.star.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;



@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ansID;

    @Column(unique = true)
    private String content;

    private String ansName;

    public String getAnsName() {
        return ansName;
    }

    public void setAnsName(String ansName) {
        this.ansName = ansName;
    }

    private String ansEmail;

    private int parentId;

    @CreatedDate
    private LocalDate createdAt  ;
   
    @ManyToOne
    private Question query;


    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<AnswerComments> answerComments = new ArrayList<>();

    public int getAnsID() {
        return ansID;
    }

    public void setAnsID(int ansID) {
        this.ansID = ansID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnsEmail() {
        return ansEmail;
    }

    public void setAnsEmail(String ansEmail) {
        this.ansEmail = ansEmail;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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
    

    public List<AnswerComments> getAnswerComments() {
        return answerComments;
    }

    public void setAnswerComments(List<AnswerComments> answerComments) {
        this.answerComments = answerComments;
    }

    @Override
    public String toString() {
        return "Answer [ansEmail=" + ansEmail + ", ansID=" + ansID + ", ansName=" + ansName + ", answerComments="
                + answerComments + ", content=" + content + ", createdAt=" + createdAt + ", parentId=" + parentId
                + ", query=" + query + "]";
    }

  

    public Answer(int ansID, String content, String ansName, String ansEmail, int parentId, LocalDate createdAt,
            Question query) {
        this.ansID = ansID;
        this.content = content;
        this.ansName = ansName;
        this.ansEmail = ansEmail;
        this.parentId = parentId;
        this.createdAt = createdAt;
        this.query = query;
    }

    public Answer() {
    }
    

    


}
