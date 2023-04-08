package com.diploma.vtt.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;

@Service
@Log4j2
public class ProcessVideoService {


    private String convertVideoToAudio(String pathToVideoFile)
    {

     String pathToAudio = "C:\\Users\\maria\\IdeaProjects\\vtt\\audio.wav";
        ProcessBuilder pb = new ProcessBuilder("ffmpeg",  "-i",   pathToVideoFile, pathToAudio).inheritIO();
        Process p = null;
        try {
            log.info("ffmpeg process has started at " + Instant.now().toString());
            p = pb.start();
            p.waitFor();
            log.info("ffmpeg process has ended at " + Instant.now().toString());
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }

     return pathToAudio;
    }

    public void passVideoToPython(String pathToVideoFile) {
        ProcessBuilder pb = new ProcessBuilder("whisper",  convertVideoToAudio(pathToVideoFile),  " --model",  "tiny" , "--device",  "cpu", "--language", "Russian").inheritIO();
        Process p = null;
        try {
            log.info("whisper process has started at " + Instant.now().toString());
            p = pb.start();

            p.waitFor();
            log.info("whisper process has ended at " + Instant.now().toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }





        BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        while (true) {
            try {
                if ((line = bfr.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("!!!");
            System.out.println(line);
        }
    }

}
