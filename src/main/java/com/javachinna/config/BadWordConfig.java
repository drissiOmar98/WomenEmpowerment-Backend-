package com.javachinna.config;

import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class BadWordConfig {

    // static Map<String, String[]> words = new HashMap<>();
    static List<String> words = new ArrayList<String>();
    public BadWordConfig() {
        loadConfigs();
    }

    static int largestWordLength = 0;
    public static void loadConfigs() {
        try {			//change this path according to your pc project file's path - so you don't get errors

            //  mte3ek path dict.txt
            //  File f = new File("C:\\Users\\Farouk Hajjej\\IdeaProjects\\spring-angular-pidev-4se5\\src\\main\\resources\\Dict.txt");
            File f = new File("/Users/macos/IdeaProjects/springPidev/src/main/resources/Dict.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String line = "";
            int counter = 0;
            while((line = reader.readLine()) != null) {
                counter++;
                String[] content = null;
                List<String> contents = null ;
                try {
                    content = line.split("_");
                    contents= Arrays.asList(content);

                    if(content.length == 0) {
                        continue;
                    }
               /*     String word = content[0];
                    String[] ignore_in_combination_with_words = new String[];
                    if(content.length > 1) {
                        ignore_in_combination_with_words = content[1].split("_");
                    }*/
                    for (String word: words) {
                        if(word.length() > largestWordLength) {
                            largestWordLength = word.length();
                        }
                    }


//                    Arrays.stream(ignore_in_combination_with_words).forEach(s -> System.out.println("ingonre"+s));

                    //words.put(word.replaceAll(" ", ""), ignore_in_combination_with_words);
                    words.addAll(contents);
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
//            System.out.println("Loaded " + counter + " words to filter out");
        } catch (  IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Iterates over a String input and checks whether a cuss word was found in a list, then checks if the word should be ignored (e.g. bass contains the word *ss).
     * @param input
     * @return
     */


    public static  ArrayList<String> badWordsFound(String input) {
        if(input == null) {

            return new ArrayList<>();
        }

//        // don't forget to remove leetspeak, probably want to move this to its own function and use regex if you want to use this
//
        input = input.replaceAll("1","i");
        input = input.replaceAll("!","i");
        input = input.replaceAll("3","e");
        input = input.replaceAll("4","a");
        input = input.replaceAll("@","a");
        input = input.replaceAll("5","s");
        input = input.replaceAll("7","t");
        input = input.replaceAll("0","o");
        input = input.replaceAll("9","g");


        ArrayList<String> badWords = new ArrayList<>();
        input = input.toLowerCase();
//        System.out.println(input);
        // iterate over each letter in the word
        for(int start = 0; start < input.length(); start++) {
            //  from each letter, keep going to find bad words until either the end of the sentence is reached, or the max word length is reached.
            for(int offset = 1; offset < (input.length()+1 - start) && offset < largestWordLength; offset++)  {
                String wordToCheck = input.substring(start, start + offset);
//                System.out.println("**********************"+wordToCheck);
                boolean ignore = false;
                if(words.contains(wordToCheck)) {
                    ignore = true ;
                    if(ignore) {
                        badWords.add(wordToCheck);
                    }
                }
            }
        }



        return badWords;
    }


    public static String filterText(String input) {
        ArrayList<String> badWords = badWordsFound(input);
//        words.forEach((s) -> {
//            System.out.println(s+ " ");
//            for (String s1 : strings){
//                System.out.println("aray"+s1);
//            }
//        });
        for(String s: badWords) {
            System.out.println(s + " qualified as a bad word in a username");
        }
        if(badWords.size() > 0) {

            return "This message was blocked";
        }
        return input;
    }
}
