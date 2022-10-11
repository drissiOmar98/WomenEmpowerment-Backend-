package com.javachinna.utils;

public class Utils {
	
	public static double calculateNegativeMarks(int totalQuestions, int totalCorrect) {
		if(totalCorrect == 0) return 0;
		double negativeMark = 0.25;
		return (totalCorrect * 1) - ((totalQuestions - totalCorrect) * negativeMark);
	}
	
	
}
