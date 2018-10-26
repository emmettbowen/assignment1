package com.example.assignment.util;

public class LogEventUtil {
	
	
	public static long calculateDuration (long timeStamp1 , long timeStamp2) {
		
		return Math.abs(timeStamp1 - timeStamp2);
	}

}
