package com.dart.Dart.Service;

import com.dart.Dart.Entity.AnswerDataset;
import com.dart.Dart.Entity.Candidate;
import com.dart.Dart.Repository.AnswerRepo;
import com.dart.Dart.Repository.CandidateRepo;
import java.io.IOException;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    @Autowired
    private AnswerRepo answerRepo;

    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private TranscribeService transcribeService;
    
    @Autowired
    private QuestionBankService questionBankService;

    public String saveCandidateAnwersTranscript(int questionId, int candidateId, String fileName,String video_path) throws IOException {
        try {
          
            AnswerDataset answer = new AnswerDataset();
            System.out.println(fileName);
            String transcript1=transcribeService.startTranscribeJob(fileName);
            String transcript=convertToJSON(transcript1);
            answer.setCandidateId(candidateId);
            answer.setQuestionId(questionId);
            answer.setQuestion_info(questionBankService.getQuestionById(questionId).getQuestiion());
            answer.setAnswerTranscript(transcript);
            answer.setAduioPath(fileName);
            answer.setVideoPath(video_path);
            answerRepo.save(answer);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }
    
    
     public String saveCandidateAnwers(int questionId, int candidateId, String answer_script) throws IOException {
        try {
          
            AnswerDataset answer = new AnswerDataset();
            answer.setCandidateId(candidateId);
            answer.setQuestionId(questionId);
              answer.setQuestion_info(questionBankService.getQuestionById(questionId).getQuestiion());
            answer.setAnswerTranscript(answer_script);
            answerRepo.save(answer);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }
    
    

    public Candidate viewCandidateById(int candidateId) {

        Optional<Candidate> opt = candidateRepo.findById(candidateId);
        if (opt.isPresent()) {
            Candidate candidate = opt.get();
            System.out.println(candidate);
            return candidate;
        }

        return null;

    }

    public Candidate signUp(Candidate signUpRequest) {
        Candidate candidate = new Candidate();
        candidate.setCandidateName(signUpRequest.getCandidateName());
        candidate.setEmail(signUpRequest.getEmail());
        candidate.setPassword(signUpRequest.getPassword());

        return candidateRepo.save(candidate);
    }

    public boolean validateCanditadeLogin(Candidate request) {
        Optional<Candidate> opt = candidateRepo.findById(request.getCandidateId());
        String password = request.getPassword();
        if (opt.isPresent()) {
            Candidate candidate = opt.get();
            if (password.equals(candidate.getPassword())) {
                return true;
            }

            return false;

        }
        else{
         return false;
        }

    }
    
   public  String convertToJSON(String transcript) {
        JSONObject jsonObject = new JSONObject();
        JSONArray opsArray = new JSONArray();

        // Split the transcript into sentences
        String[] sentences = transcript.split("\\.\\s*");

        // Add each sentence as an insert operation to opsArray
        for (String sentence : sentences) {
            JSONObject insertObject = new JSONObject();
            insertObject.put("insert", sentence);
            opsArray.put(insertObject);
        }

        // Add the opsArray to the main JSON object
        jsonObject.put("ops", opsArray);

        return jsonObject.toString();
    }   
    

}
