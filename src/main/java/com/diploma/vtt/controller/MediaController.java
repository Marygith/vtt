package com.diploma.vtt.controller;

import com.diploma.vtt.model.Doc;
import com.diploma.vtt.model.VideoEntity;
import com.diploma.vtt.repository.VideoRepository;
import com.diploma.vtt.service.ProcessTextService;
import com.diploma.vtt.service.ProcessVideoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MediaController {

    private final ProcessVideoService processVideoService;

    private final ProcessTextService processTextService;
    private final VideoRepository videoRepository;

    private SseEmitter sseEmitter = new SseEmitter();

    Doc textEntity = new Doc(1, "there is some text to display", "test", "me");

    public MediaController(ProcessVideoService processVideoService, ProcessTextService processTextService, VideoRepository videoRepository) {
        this.processVideoService = processVideoService;
        this.processTextService = processTextService;
        this.videoRepository = videoRepository;
    }

/*    @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {

        return Flux.interval(Duration.ofSeconds(1)).map(s -> "Flux - " + LocalTime.now().toString());
    }*/

    @GetMapping("/events")
    public SseEmitter getEvents() {
        sseEmitter = new SseEmitter();
        // create and send events
//        for (int i = 0; i < 10; i++) {
            try {
                sseEmitter.send(SseEmitter.event().name("message").data(ProcessTextService.getText()));
                sseEmitter.complete();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//        }
//        sseEmitter.complete(); // close the emitter when done sending events
        return sseEmitter;
    }
/*    @GetMapping("/media")
    public TextEntity getText() {

        processTextService.divideTextOnParagraphs("C:\\\\Users\\\\maria\\\\Desktop\\\\new_markina_trans.txt");
        return new TextEntity(1, ProcessTextService.getText(), "new title", "still me");
    }*/

    @GetMapping("/media")
    public Doc getText() {

        sendEvent();
//        processTextService.divideTextOnParagraphs("C:\\\\Users\\\\maria\\\\Desktop\\\\new_markina_trans.txt");
        return new Doc(1, ProcessTextService.getText(), "new title", "still me");
    }

    @PostMapping(value = "/media", consumes = /*MediaType.APPLICATION_FORM_URLENCODED_VALUE*/MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Doc> saveText(@RequestBody Doc entity) {
        System.out.println(entity.getBody());
        System.out.println(entity.getId());
       return new ResponseEntity<>(entity, HttpStatus.ACCEPTED);
    }

    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uploadImage(@RequestParam("videoFile") MultipartFile file) throws IOException {

        log.info("Original Image Byte Size - " + file.getBytes().length);
        VideoEntity video = new VideoEntity(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));
        FileOutputStream out = new FileOutputStream("C:\\Users\\maria\\IdeaProjects\\vtt\\video.mp4");
        out.write(file.getBytes());

        out.close();
        videoRepository.save(video);
        log.info("Path to file is video.mp4");
        processVideoService.passVideoToPython("C:\\Users\\maria\\IdeaProjects\\vtt\\video.mp4");
        return ResponseEntity.status(HttpStatus.OK);
    }

    public static byte[] compressBytes(byte[] data) {
        System.out.println(data.length);
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            System.out.println(outputStream.toByteArray().length);
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed video Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ioe) {
        }
        return outputStream.toByteArray();
    }

    public  void sendEvent() {
//        try {

            ProcessTextService.setText("something new");
           /* sseEmitter = new SseEmitter();
            sseEmitter.send(SseEmitter.event().name("message").data("done"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sseEmitter.complete();
        return sseEmitter;*/
    }

}
