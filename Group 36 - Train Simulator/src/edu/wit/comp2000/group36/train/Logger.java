package edu.wit.comp2000.group36.train;

import java.io.IOException;
import java.io.PrintWriter;


public class Logger {
	
	private static PrintWriter pw;
	
	static {
		
		try {
			pw = new PrintWriter("TrainSimulator.log");
		}
		catch (IOException e){
		}
		
	}
	
	public static void logging(String s) {
		pw.println(s);
		pw.flush();
	}
	
	public static void main (String[]args) {
		logging("my log");
	}

}
