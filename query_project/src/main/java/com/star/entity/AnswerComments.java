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
@Table(name = "Comments_in_Answers")
public class AnswerComments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int acId;

    @Column(name = "ac_Data")
    private String acData;

    private int parentId;
    private String acName;
    private String acEmail;

    @CreatedDate
    private LocalDate createdAt;
   
    @ManyToOne
    private Answer answer;

    public int getAcId() {
        return acId;
    }

    public void setAcId(int acId) {
        this.acId = acId;
    }

    public String getAcData() {
        return acData;
    }

    public void setAcData(String acData) {
        this.acData = acData;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getAcEmail() {
        return acEmail;
    }

    public void setAcEmail(String acEmail) {
        this.acEmail = acEmail;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "AnswerComments [acData=" + acData + ", acEmail=" + acEmail + ", acId=" + acId + ", acName=" + acName
                + ", answer=" + answer + ", createdAt=" + createdAt + ", parentId=" + parentId + "]";
    }

    public AnswerComments(int acId, String acData, int parentId, String acName, String acEmail, LocalDate createdAt,
            Answer answer) {
        this.acId = acId;
        this.acData = acData;
        this.parentId = parentId;
        this.acName = acName;
        this.acEmail = acEmail;
        this.createdAt = createdAt;
        this.answer = answer;
    }

    public AnswerComments() {
    }
    


    
}
