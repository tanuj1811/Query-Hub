package com.star.entity;



import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;








@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private int userId;

	private String name;
    @Column(unique = true)
    private String email;
    

    private String passward;
    private String role;
    private long number = 123;
    private String profession = "not set";
    private String address = "abc,India";
    private String website = "not set";
    private String github = "not set";
    private String discord = "not set";
    private String instagram = "not set";

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<Question> queries = new ArrayList<>();
    
    
    
    
    public long getNumber() {
        return number;
    }
    public void setNumber(long number) {
        this.number = number;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getGithub() {
        return github;
    }
    public void setGithub(String github) {
        this.github = github;
    }
    public String getDiscord() {
        return discord;
    }
    public void setDiscord(String discord) {
        this.discord = discord;
    }
    public String getInstagram() {
        return instagram;
    }
    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
    public String getProfession() {
        return profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassward() {
        return passward;
    }
    public void setPassward(String passward) {
        this.passward = passward;
    }
    @Override
    public String toString() {
        return "User [email=" + email + ", name=" + name + ", passward=" + passward + ", queries=" + queries + ", role="
                + role + ", userId=" + userId + "]";
    }
    public User(int userId, String name, String email, String passward) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passward = passward;
    }
    public List<Question> getQueries() {
        return queries;
    }
    public void setQueries(List<Question> queries) {
        this.queries = queries;
    }
    
    public User() {
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public User(int userId, String name, String email, String passward, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passward = passward;
        this.role = role;
       
    }
    

    

}

