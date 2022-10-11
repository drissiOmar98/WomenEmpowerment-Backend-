package com.javachinna.sentimentAnalyzer;

import java.nio.file.Path;


public class FileUtils {

  private static final String TEXT_FILE_EXTENSION = ".txt";

  public static boolean isTextFile(Path path) {
    return path != null && path.toString().endsWith(TEXT_FILE_EXTENSION);
  }
}
