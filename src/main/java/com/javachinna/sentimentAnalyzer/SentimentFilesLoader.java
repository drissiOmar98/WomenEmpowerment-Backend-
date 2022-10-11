package com.javachinna.sentimentAnalyzer;



import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SentimentFilesLoader implements SentimentLoader {

  private static final String AFINN_DIRECTORY_NAME = "afinn";
  private static final String TAB_REGEX = "\\t";

  @Override
  public Map<String, Integer> loadData() {
    return getFilePaths().stream()
        .map(this::parseTsvFileForSentimentTextAndScore)
        .collect(HashMap::new, Map::putAll, Map::putAll);
  }

  private List<Path> getFilePaths() throws ResourceNotFoundException {
    try {
      URI directoryUri = getClass().getClassLoader().getResource(AFINN_DIRECTORY_NAME).toURI();

      return Files.list(Paths.get(directoryUri))
          .filter(Files::isRegularFile)
          .filter(FileUtils::isTextFile)
          .collect(Collectors.toList());

    } catch (Exception ex) {
      throw new ResourceNotFoundException(String.format("Failed to find %s directory.", AFINN_DIRECTORY_NAME), ex);
    }
  }

  private Map<String, Integer> parseTsvFileForSentimentTextAndScore(Path path) throws SentimentParseException {
    try {
      return Files.lines(path)
          .map(l -> l.split(TAB_REGEX))
          .collect(Collectors.toMap(l -> l[0], l -> Integer.valueOf(l[1])));

    } catch (Exception ex) {
      throw new SentimentParseException(String.format("Failed to parse sentiment file: %s", path), ex);
    }
  }
}
