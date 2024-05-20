package com.dart.Dart.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="QuestionBank")

public class QuestionBank {
  
  @Id 
  @GeneratedValue(strategy = GenerationType.AUTO) 
  private int question_id;
  private String questiion;
  private String type;

  
  
  
  
    public QuestionBank(int question_id, String questiion, String type) {
        this.question_id = question_id;
        this.questiion = questiion;
        this.type = type;
    }

    public QuestionBank() {
    }

    
    
    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestiion() {
        return questiion;
    }

    public void setQuestiion(String questiion) {
        this.questiion = questiion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
  
  
}
