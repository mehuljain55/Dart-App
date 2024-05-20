package com.dart.Dart.Controller;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dart.Dart.Service.CandidateService;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class VideoController {
    
    @Autowired
    private CandidateService candidateService;

    private final String bucketName = "dart-app1";

    private final String accessKey = ""; // Replace with your access key
    private final String secretAceessKey = ""; // Replace with your secret key
    
    
    
    @PostMapping("/video/upload")
    public ResponseEntity<String> uploadVideoToS3(@RequestParam("videoFile") MultipartFile videoFile) {
        if (videoFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Video file is empty");
        }
        System.out.println("Video controller accesed");
        String key = videoFile.getOriginalFilename();
        System.out.println(key);
        String[] parts = key.split("[._]");
        String candidateId1 = parts[0]; // Extract candidate ID
        String questionId1 = parts[1]; // Extract question ID
        int candidateId=Integer.parseInt(candidateId1);
        int questionId=Integer.parseInt(questionId1);

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretAceessKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("s3.amazonaws.com", "ap-southeast-2")) // Modify endpoint according to your region
                .build();

        try {
            InputStream inputStream = videoFile.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(videoFile.getSize());
            metadata.setContentType(videoFile.getContentType());
            s3Client.putObject(new PutObjectRequest(bucketName, key, inputStream, metadata));
            inputStream.close();
            String s3Url = "https://" + bucketName + ".s3.amazonaws.com/" + key;
            System.out.println(s3Url);
             String staus=candidateService.saveCandidateAnwersTranscript(questionId, candidateId, videoFile.getOriginalFilename(),s3Url);
            return ResponseEntity.ok(s3Url);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload video");
        }
    }
}
