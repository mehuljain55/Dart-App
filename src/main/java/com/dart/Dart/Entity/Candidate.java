package com.dart.Dart.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;


@Entity
@Table(name="CandidateInformation")

public class Candidate {
    
   @Id 
   @GeneratedValue(strategy = GenerationType.AUTO) 
    private int candidateId;
    private String candidateName;
    private String email;
    private String password;
  
    @OneToMany
    @JoinColumn(name = "candidateId") // Name of the foreign key column in the AnswerDataset table
    private List<AnswerDataset> answerDatasets;
    
    
    public Candidate(int candidateId, String candidateName, String email) {
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.email = email;
    }
    

    public Candidate() {
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    

    public List<AnswerDataset> getAnswerDatasets() {
        return answerDatasets;
    }

    public void setAnswerDatasets(List<AnswerDataset> answerDatasets) {
        this.answerDatasets = answerDatasets;
    }

    @Override
    public String toString() {
        return "Candidate{" + "candidateId=" + candidateId + ", candidateName=" + candidateName + ", email=" + email + ", answerDatasets=" + answerDatasets + '}';
    }


}
