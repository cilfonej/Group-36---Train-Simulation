package edu.wit.comp2000.group36.train;

import java.util.Random;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Logger {
	public static final Random RAND = new Random();

	public static void main(String[] args) {

		Logger logger = Logger.getLogger("Train Simulator Log");
		FileHandler fh;

		try {
			fh = new FileHandler("C:/"); // insert file path
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Logger getLogger(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private void addHandler(FileHandler fh) {
		// TODO Auto-generated method stub

	}


}
