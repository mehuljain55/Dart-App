package com.dart.Dart.Service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.*;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.protobuf.ByteString;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.springframework.stereotype.Service;

@Service
public class SpeechToTextService {

    /*
     Required parameter Audio file path
     transcibe(String wavFilePath)
     return value string
     */
    
    
    public static byte[] convertStereoToMono(byte[] stereoData) {
        try {

         
            ByteArrayInputStream bais = new ByteArrayInputStream(stereoData);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bais);

            // Convert AudioInputStream to mono
            AudioFormat sourceFormat = audioInputStream.getFormat();
            AudioFormat targetFormat = new AudioFormat(
                    sourceFormat.getEncoding(),
                    sourceFormat.getSampleRate(),
                    16, // 16 bits per sample
                    1, // Mono
                    sourceFormat.getFrameSize() / sourceFormat.getChannels(), // Frame size
                    sourceFormat.getFrameRate(),
                    sourceFormat.isBigEndian()
            );
            AudioInputStream monoAudioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);

            // Convert AudioInputStream to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int len;
            while ((len = monoAudioInputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String transcibe(String filename) throws FileNotFoundException, IOException {

        String transcribe = "";
        // Replace '/path/to/your/service-account-file.json' with the path to your service account key file

        
        String wavFilePath="src/main/resources/audio/"+filename+".wav";
        AppCredentials credential = new AppCredentials();

        // Explicitly load the credentials
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credential.getCredentialsFilePath()))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/cloud-platform"));

        // Instantiates a client with the loaded credentials
        try (SpeechClient speechClient = SpeechClient.create(SpeechSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build())) {

            // Replace 'path/to/your/audio/file.mp3' with the path to your MP3 audio file
            // Read the audio file into memory
            Path path = Paths.get(wavFilePath);
            byte[] data = Files.readAllBytes(path);

            // Convert stereo audio to mono
            byte[] monoData = convertStereoToMono(data);
            if (monoData == null) {
                System.out.println("Failed to convert stereo audio to mono.");
                return null;
            }

            ByteString audioBytes = ByteString.copyFrom(monoData);

            // Configure recognition settings
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(AudioEncoding.LINEAR16)
                    .setSampleRateHertz(48000) // Update the sample rate to match the audio file
                    .setLanguageCode("en-US")
                    .build();

            // Builds the audio content for recognition
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            // Performs speech recognition on the audio file
            RecognizeResponse response = speechClient.recognize(config, audio);

            for (SpeechRecognitionResult result : response.getResultsList()) {

                transcribe = transcribe + result.getAlternativesList().get(0).getTranscript();
            }

        }
        return transcribe;
    }
    
    
    
             
}
