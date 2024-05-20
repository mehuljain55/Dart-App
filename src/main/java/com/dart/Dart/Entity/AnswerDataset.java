package com.dart.Dart.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="AnswerDataset")
public class AnswerDataset {
   
  @Id 
  @GeneratedValue(strategy = GenerationType.AUTO) 
  private int answerId;
  private  int candidateId;
  private int questionId;
  private String question_info;
  private String answerTranscript;
  private String aduioPath;
    private String videoPath;

    public AnswerDataset(int answerId, int candidateId, int questionId, String answerTranscript, String aduioPath) {
        this.answerId = answerId;
        this.candidateId = candidateId;
        this.questionId = questionId;
        this.answerTranscript = answerTranscript;
        this.aduioPath = aduioPath;
    }
  


    public AnswerDataset() {
    }

  
    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerTranscript() {
        return answerTranscript;
    }

    public void setAnswerTranscript(String answerTranscript) {
        this.answerTranscript = answerTranscript;
    }

    public String getAduioPath() {
        return aduioPath;
    }

    public void setAduioPath(String aduioPath) {
        this.aduioPath = aduioPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getQuestion_info() {
        return question_info;
    }

    public void setQuestion_info(String question_info) {
        this.question_info = question_info;
    }

  
    
    
    @Override
    public String toString() {
        return "AnswerDataset{" + "answerId=" + answerId + ", candidateId=" + candidateId + ", questionId=" + questionId + ", answerTranscript=" + answerTranscript + ", aduioPath=" + aduioPath + '}';
    }
  
  
  
}
