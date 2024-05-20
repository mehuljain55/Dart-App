package com.dart.Dart.Controller;

import com.dart.Dart.Service.CandidateService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AnswerDatasetController {
    
    @Autowired
    private CandidateService candidateService;
    
    
    @PostMapping("/post/answer")
    public String submit(@RequestParam("candidateId") int candidateId,
                         @RequestParam("questionID") int questionID,
                         @RequestParam("answer") String answer) throws IOException
    {
    
        String status=candidateService.saveCandidateAnwers(questionID, candidateId, answer);
        return "status";
        
    }
}
