package com.diploma.vtt.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ProcessTextService {

    private static String text = "not so default text";

    public static String getText() {return text;}

    public static void setText(String text) {
        ProcessTextService.text = text;
    }
/*    public static void main(String[] args) {

        divideTextOnParagraphs("C:\\Users\\maria\\Desktop\\new_markina_trans.txt");
    }*/
    public /*static*/ String divideTextOnParagraphs(String pathToTextFile) {
        String[] list = { "python", "-u", "C:\\Users\\maria\\IdeaProjects\\vtt\\src\\main\\java\\com\\diploma\\vtt\\service\\split_script.py", pathToTextFile };
        ProcessBuilder pb = new ProcessBuilder().command( "python", "C:\\Users\\maria\\IdeaProjects\\vtt\\src\\main\\java\\com\\diploma\\vtt\\service\\split_script.py", pathToTextFile);

        Process p = null;
        try {
            p = pb.start();
            //System.out.println("" + pb.command());
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line);
//            System.out.println(line);
        }
        System.out.println(sb);
        File file = new File("file.txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.append(sb);
             writer.close();
        }  catch (Exception e) {

        }
//        System.out.println("end");
        return sb.toString();
    }
}
