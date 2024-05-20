package com.dart.Dart.Service;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.transcribe.AmazonTranscribe;
//import com.amazonaws.services.transcribe.AmazonTranscribeClientBuilder;
//import com.amazonaws.services.transcribe.model.DeleteTranscriptionJobRequest;
//import com.amazonaws.services.transcribe.model.ListTranscriptionJobsRequest;
//import com.amazonaws.services.transcribe.model.ListTranscriptionJobsResult;
//import com.amazonaws.services.transcribe.model.TranscriptionJobSummary;
//
//
//
//public class Test {
// 
//    public static void main(String[] args) {
// 
//        String accessKey = ""; // Replace with your access key
//        String secretKey = ""; // Replace with your secret key
//
//        // Create AWS credentials
//        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
//
//        // Create an Amazon Transcribe client
//        AmazonTranscribe transcribeClient = AmazonTranscribeClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                .withRegion(Regions.AP_SOUTHEAST_2)
//                .build();
//
//        // List and delete all transcription jobs
//        ListTranscriptionJobsRequest listRequest = new ListTranscriptionJobsRequest();
//        ListTranscriptionJobsResult listResult;
//        do {
//            listResult = transcribeClient.listTranscriptionJobs(listRequest);
//            for (TranscriptionJobSummary job : listResult.getTranscriptionJobSummaries()) {
//                String jobName = job.getTranscriptionJobName();
//                System.out.println("Deleting job: " + jobName);
//               transcribeClient.deleteTranscriptionJob(new DeleteTranscriptionJobRequest().withTranscriptionJobName(jobName));
//            }
//            listRequest.setNextToken(listResult.getNextToken());
//        } while (listResult.getNextToken() != null);
//
//        System.out.println("All transcription jobs have been deleted.");
//    }    
//}
