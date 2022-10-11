package com.javachinna.sentimentAnalyzer;

import java.util.Map;


public class SentimentResult {
  private int score;
  private String state;
  private Map<String, Integer> detectedWords;

  private SentimentResult(SentimentResultBuilder builder) {
    this.score = builder.score;
    this.state = builder.state;
    this.detectedWords = builder.detectedWords;

  }

  public int getScore() {
    return score;
  }

  public String getState() {
    return state;
  }

  public Map<String, Integer> getDetectedWords() {
    return detectedWords;
  }


  public static class SentimentResultBuilder {
    private int score;
    private String state;
    private Map<String, Integer> detectedWords;

    public SentimentResultBuilder setScore(int score) {
      this.score = score;
      return this;
    }

    public SentimentResultBuilder setState(String state) {
      this.state = state;
      return this;
    }

    public SentimentResultBuilder setDetectedWords(Map<String, Integer> detectedWords) {
      this.detectedWords = detectedWords;
      return this;
    }

    public SentimentResult build() {
      return new SentimentResult(this);
    }
  }
}
