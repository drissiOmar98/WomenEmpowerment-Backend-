package com.javachinna.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterBW {

	static Map<String, String[]> words = new HashMap<>();
	static int largestWordLength = 0;

	public static void loadConfigs() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(
					"https://docs.google.com/spreadsheets/d/1hIEi2YG3ydav1E06Bzf2mQbGZ12kh2fe4ISgLg_UBuM/export?format=csv")
							.openConnection().getInputStream()));
			String line = "";
			int counter = 0;
			while ((line = reader.readLine()) != null) {
				counter=counter+1;
				String[] content = null;
				try {
					content = line.split(",");
					if (content.length == 0) {
						continue;
					}
					String word = content[0];
					String[] ignoreInCombinationWithWords = new String[] {};
					if (content.length > 1) {
						ignoreInCombinationWithWords = content[1].split("_");
					}

					if (word.length() > largestWordLength) {
						largestWordLength = word.length();
					}
					words.put(word.replaceAll(" ", ""), ignoreInCombinationWithWords);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Iterates over a String input and checks whether a cuss word was found in a
	 * list, then checks if the word should be ignored (e.g. bass contains the word
	 * *ss).
	 * 
	 * @param input
	 * @return
	 */
	public static List<String> badWordsFound(String input) {
		if (input == null) {
			return new ArrayList<>();
		}

		// remove leetspeak
		input = input.replaceAll("1", "i");
		input = input.replaceAll("!", "i");
		input = input.replaceAll("3", "e");
		input = input.replaceAll("4", "a");
		input = input.replaceAll("@", "a");
		input = input.replaceAll("5", "s");
		input = input.replaceAll("7", "t");
		input = input.replaceAll("0", "o");
		input = input.replaceAll("9", "g");

		ArrayList<String> badWords = new ArrayList<>();
		input = input.toLowerCase().replaceAll("[^a-zA-Z]", "");

		// iterate over each letter in the word
		for (int start = 0; start < input.length(); start++) {
			// from each letter, keep going to find bad words until either the end of the
			// sentence is reached, or the max word length is reached.
			for (int offset = 1; offset < (input.length() + 1 - start) && offset < largestWordLength; offset++) {
				String wordToCheck = input.substring(start, start + offset);
				if (words.containsKey(wordToCheck)) {
					// for example, if you want to say the word bass, that should be possible.
					String[] ignoreCheck = words.get(wordToCheck);
					boolean ignore = false;
					for (int s = 0; s < ignoreCheck.length; s++) {
						if (input.contains(ignoreCheck[s])) {
							ignore = true;
							break;
						}
					}
					if (!ignore) {
						badWords.add(wordToCheck);
					}
				}
			}
		}
		for(String s: badWords) {
			System.out.println(s + " qualified as a bad word in a username");
		}



		return badWords;

	}

	public static List<String> filterText(String input) {
		return badWordsFound(input);
	}

	public static String filterWord(String input) {
		for (String word : FilterBW.filterText(input)) {
			input = input.replaceAll(word, "********");
		}
		
		return input;
	}
	
}
