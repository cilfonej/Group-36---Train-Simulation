package edu.wit.comp2000.group36.train;

/**
*@author Leslie Vongphakdy
*
*This class sets up a log for each class to use.
*The log will hold info in the file
*'TrainSimulator.log'. It will store information
*about the stations where trains enter and depart.
**/

import java.io.IOException;
import java.io.PrintWriter;


public class Logger {
	
	/**
	*PrintWriter allows users to log into
	*the log file.
	**/
	private static PrintWriter pw;
	
	static {
		
		try {
			pw = new PrintWriter("TrainSimulator.log");
		}
		catch (IOException e){
		}
		
	}
	
	/**
	*Prints info to the log
	**/
	public static void logging(String s) {
		pw.println(s);
		pw.flush();
	}
	/**
	*Test method, log 'Group 36: Train Tracking Log' into TrainSimulator.log
	**/
	public static void main (String[]args) {
		logging("Group 36: Train Tracking Log");
	}

}
