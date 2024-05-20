package com.dart.Dart.Repository;

import com.dart.Dart.Entity.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<QuestionBank, Integer> {
    
}
