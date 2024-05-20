package com.dart.Dart.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.transcribe.AmazonTranscribe;
import com.amazonaws.services.transcribe.AmazonTranscribeClientBuilder;
import com.amazonaws.services.transcribe.model.DeleteTranscriptionJobRequest;
import com.amazonaws.services.transcribe.model.GetTranscriptionJobRequest;
import com.amazonaws.services.transcribe.model.GetTranscriptionJobResult;
import com.amazonaws.services.transcribe.model.LanguageCode;
import com.amazonaws.services.transcribe.model.Media;
import com.amazonaws.services.transcribe.model.MediaFormat;
import com.amazonaws.services.transcribe.model.StartTranscriptionJobRequest;
import com.amazonaws.services.transcribe.model.StartTranscriptionJobResult;
import com.amazonaws.services.transcribe.model.TranscriptionJob;
import com.amazonaws.services.transcribe.model.TranscriptionJobStatus;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TranscribeService {
    private final String accessKey = ""; // Replace with your access key
    private final String secretKey = ""; // Replace with your secret key
    private final String bucketName = "";

    
    private String fetchTranscript(String transcriptUri) {
        StringBuilder transcript = new StringBuilder();
        try {
            URL url = new URL(transcriptUri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                transcript.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transcript.toString();
    }

    private String extractTranscriptFromJSON(String jsonResult) {
        JSONObject jsonObject = new JSONObject(jsonResult);
        JSONArray transcriptsArray = jsonObject.getJSONObject("results").getJSONArray("transcripts");
        StringBuilder transcriptBuilder = new StringBuilder();
        for (int i = 0; i < transcriptsArray.length(); i++) {
            JSONObject transcriptObject = transcriptsArray.getJSONObject(i);
            String transcript = transcriptObject.getString("transcript");
            transcriptBuilder.append(transcript);
        }
        return transcriptBuilder.toString();
    } 
    
     private void deleteTranscriptionJob(AmazonTranscribe transcribeClient, String jobName) {
        DeleteTranscriptionJobRequest deleteTranscriptionJobRequest = new DeleteTranscriptionJobRequest()
                .withTranscriptionJobName(jobName);
        transcribeClient.deleteTranscriptionJob(deleteTranscriptionJobRequest);
        System.out.println("Transcription job deleted: " + jobName);
    }

     

   public String startTranscribeJob(String file_name)
   {
   
       
        String mediaFileUri = "https://dart-app1.s3.amazonaws.com/"+file_name;
        String transcribeJobName = "TranscriptionJob_"+file_name;
        String outputFolder = "TranscriptHeaders/"; // Specify your desired folder
        
        // Create AWS credentials
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        // Create an Amazon Transcribe client
        AmazonTranscribe transcribeClient = AmazonTranscribeClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_SOUTHEAST_2)
                .build();

        // Create an Amazon S3 client
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_SOUTHEAST_2)
                .build();

        // Check if the S3 bucket and file exist
        if (!s3Client.doesBucketExistV2(bucketName)) {
            System.out.println("Bucket does not exist.");
            return "Bucket does not exists";
        }

        // Create and start the transcription job
        StartTranscriptionJobRequest startTranscriptionJobRequest = new StartTranscriptionJobRequest()
                .withTranscriptionJobName(transcribeJobName)
                .withLanguageCode(LanguageCode.EnUS)
                .withMediaFormat(MediaFormat.Webm)
                .withMedia(new Media().withMediaFileUri(mediaFileUri))
                .withOutputBucketName(bucketName)
                .withOutputKey(outputFolder + transcribeJobName + ".json");

        StartTranscriptionJobResult startTranscriptionJobResult = transcribeClient.startTranscriptionJob(startTranscriptionJobRequest);
        System.out.println("Transcription job started: " + startTranscriptionJobResult.getTranscriptionJob().getTranscriptionJobName());

        // Poll the job status until it is completed
        GetTranscriptionJobRequest getTranscriptionJobRequest = new GetTranscriptionJobRequest()
                .withTranscriptionJobName(transcribeJobName);

        TranscriptionJob transcriptionJob;
        do {
            GetTranscriptionJobResult getTranscriptionJobResult = transcribeClient.getTranscriptionJob(getTranscriptionJobRequest);
            transcriptionJob = getTranscriptionJobResult.getTranscriptionJob();
            System.out.println("Current status: " + transcriptionJob.getTranscriptionJobStatus());
            try {
                Thread.sleep(5000); // Wait for 5 seconds before polling again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (transcriptionJob.getTranscriptionJobStatus().equals(TranscriptionJobStatus.IN_PROGRESS.toString()));

        // Check if the transcription job is completed
        if (transcriptionJob.getTranscriptionJobStatus().equals(TranscriptionJobStatus.COMPLETED.toString())) {            
            String transcriptUri = transcriptionJob.getTranscript().getTranscriptFileUri();
            String transcriptJSON = fetchTranscript(transcriptUri);
            String transcript = extractTranscriptFromJSON(transcriptJSON);    
            System.out.println(transcript);            
            deleteTranscriptionJob(transcribeClient, transcribeJobName);
            return transcript;
            
        } else {
            System.out.println("Transcription failed with status: " + transcriptionJob.getTranscriptionJobStatus());
            deleteTranscriptionJob(transcribeClient, transcribeJobName);

            return null;
        }
 
   }
  
}