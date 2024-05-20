package com.dart.Dart.Service;

import com.dart.Dart.Entity.QuestionBank;
import com.dart.Dart.Repository.QuestionRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionBankService {


    @Autowired
    private QuestionRepo repo;

    public List<QuestionBank> getAllQuestion() {
        return repo.findAll();
    }
    
    
    public QuestionBank getQuestionById(int questionId) {
        Optional<QuestionBank> opt=repo.findById(questionId);
        if(opt.isPresent())
        {
          QuestionBank question_detail=opt.get();
          return question_detail;
        }
        else{
          return null;
        }
    }

}
