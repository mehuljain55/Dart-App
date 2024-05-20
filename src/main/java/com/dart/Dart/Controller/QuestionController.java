package com.dart.Dart.Controller;

import com.dart.Dart.Entity.QuestionBank;
import com.dart.Dart.Service.QuestionBankService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    @Autowired
    private QuestionBankService service;

    
    @GetMapping("/getAllQuestion")
    public List<QuestionBank> getAllQuestion() {
        
        return service.getAllQuestion();
    }

    
    @GetMapping("/getQuestionId")
    public QuestionBank getQuestionById(@RequestParam("questionId") int questionId)
    {
        return service.getQuestionById(questionId);
    }
     
}
