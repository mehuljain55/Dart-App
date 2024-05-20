/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dart.Dart.Repository;

import com.dart.Dart.Entity.AnswerDataset;
import com.dart.Dart.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnswerRepo extends JpaRepository<AnswerDataset, Integer> {
    
}
