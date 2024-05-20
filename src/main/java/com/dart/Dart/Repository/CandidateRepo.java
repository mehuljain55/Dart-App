package com.dart.Dart.Repository;

import com.dart.Dart.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepo extends JpaRepository<Candidate, Integer>{
    
}
