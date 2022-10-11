package com.javachinna.sentimentAnalyzer;

import java.util.Map;

public interface SentimentLoader {

  /**
   * Loads AFINN text and sentiment scores into a {@link Map}
   * @return a map containing text and sentiment scores
   */
  Map<String, Integer> loadData();
}
