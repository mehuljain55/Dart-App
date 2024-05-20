package com.dart.Dart.Controller;

import com.dart.Dart.Entity.Candidate;
import com.dart.Dart.Service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CandidateController {

    
    
    @Autowired
    private CandidateService candidateService;
    
    @PostMapping("/validatelogin")
    public boolean validateLogin(@RequestBody Candidate request)
    {
        boolean status=candidateService.validateCanditadeLogin(request);
        System.out.println(status);
        return status;
    }
    
    @PostMapping("/signup")
    public Candidate signUp(@RequestBody Candidate signUpRequest) {
        Candidate candidate = candidateService.signUp(signUpRequest);
        return candidate;
    }
    
    @GetMapping("/show")
    public Candidate show(@RequestParam("candidateId") int candidateId)
    {
      return candidateService.viewCandidateById(candidateId);
    }
    
    
    
    
    
}
