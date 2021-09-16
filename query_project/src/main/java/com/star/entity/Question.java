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
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Queries")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int queryId;

    // @NotBlank(message = "Email must be there for notification")
    @Column(name = "query_email",unique = false)
    private String email;
    @NotBlank
    private String title;

    @Column(length = 5000)
    private String detail;
    
    @Column(name = "user_email")
    private String queryEmail;
    private String imgURL;
    private LocalDate date = java.time.LocalDate.now() ;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(cascade =CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade =CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();

    public int getQueryId() {
        return queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    

    public String getQueryEmail() {
        return queryEmail;
    }

    public void setQueryEmail(String queryEmail) {
        this.queryEmail = queryEmail;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    
    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Question() {
    }

    



    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    
    @Override
    public String toString() {
        return "Question [answers=" + answers + ", comments=" + comments + ", date=" + date + ", detail=" + detail
                + ", email=" + email + ", imgURL=" + imgURL + ", queryEmail=" + queryEmail + ", queryId=" + queryId
                + ", title=" + title + ", user=" + user + "]";
    }

    public Question(int queryId, String email, @NotBlank String title, String detail, String queryEmail, String imgURL,
            LocalDate date, User user, List<Comment> comments, List<Answer> answers) {
        this.queryId = queryId;
        this.email = email;
        this.title = title;
        this.detail = detail;
        this.queryEmail = queryEmail;
        this.imgURL = imgURL;
        this.date = date;
        this.user = user;
        this.comments = comments;
        this.answers = answers;
    }
    
    
    
}