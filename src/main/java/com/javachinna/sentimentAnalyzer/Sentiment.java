package com.javachinna.sentimentAnalyzer;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class Sentiment {

  private static Map<String, Integer> sentimentsTextAndScore;

  private Sentiment() {
    SentimentLoader sentimentFileLoader = new SentimentFilesLoader();
    sentimentsTextAndScore = sentimentFileLoader.loadData();
  }

  /**
   * Perform sentiment analysis on provided text.
   * @param text to be analysed
   * @return sentiment result that contains the score, state and detected words
   */
  public SentimentResult analyze(String text) {

    if(text == null || text.isEmpty())
      throw new IllegalArgumentException("The text parameter must be provided.");

    String[] words = text.toLowerCase().split(" ");
    AtomicInteger score = new AtomicInteger(0);

    Map<String, Integer> detectedWords = Stream.of(words)
        .filter(w -> sentimentsTextAndScore.containsKey(w))
        .peek(w -> score.addAndGet(sentimentsTextAndScore.get(w)))
        .collect(Collectors.toMap(w -> w, sentimentsTextAndScore::get));

    return new SentimentResult.SentimentResultBuilder()
        .setScore(score.get())
        .setState(getState(score.get()))
        .setDetectedWords(detectedWords)
        .build();
  }

  private String getState(int score) {
    if (score > 0)
      return SentimentState.POSITIVE.name();
    else if (score < 0)
      return SentimentState.NEGATIVE.name();
    else
      return SentimentState.NEUTRAL.name();
  }

  public static Sentiment getInstance() {
    return SingletonHelper.INSTANCE;
  }

  private static class SingletonHelper {
    private static final Sentiment INSTANCE = new Sentiment();
  }


}
