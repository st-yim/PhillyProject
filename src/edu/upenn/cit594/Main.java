package edu.upenn.cit594;

import java.io.IOException;

import edu.upenn.cit594.datamanagement.JsonReader;
import edu.upenn.cit594.datamanagement.TweetReader;
import edu.upenn.cit594.datamanagement.TxtReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.TweetProcessor;
import edu.upenn.cit594.ui.CommandLineUserInterface;

public class Main {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Error: not enough files provided");
			return; 
		}
		if (args.length > 3) {
			System.out.println("Error: too many files provided");
			return; 
		}
		// argument 1 = tweet file (JSON or txt)
		String tweetFile = args[0];

		// argument 2 = state file (CSV)
		String stateFile = args[1];
		
		// argument 3 = log file
		String logFile = args[2];
		
		// initialize logger 
		Logger logger = Logger.getInstance();
		logger.setOutput(logFile);

		/*
		 * UnsupportedOperationException will be thrown if file is unsupported. The throw is initiated in
		 * TweetReader, getFileExtension().
		 * TweetReader implements AutoCloseable, the reader (reader1 and reader 2) will be automatically
		 * closed once the try block is exited.
		 */

		try(TweetReader reader1 = new TweetReader(tweetFile);
				TweetReader reader2 = new TweetReader(stateFile);) {

			String reader1Extension = reader1.getFileExtension();
			TweetProcessor processor1 = null;

			if (reader1Extension.equals("json")) {
				JsonReader jsonReader = new JsonReader(tweetFile);
				processor1 = new TweetProcessor(jsonReader);
			} else if (reader1Extension.equals("txt")) {
				TxtReader txtReader = new TxtReader(tweetFile);
				processor1 = new TweetProcessor(txtReader);
			} else {
				// first argument must be a CSV file
				throw new IllegalArgumentException("Invalid, the first input file: " + reader1.getFileName() + " is neither json nor txt");
			}

			String reader2Extension = reader2.getFileExtension();
			TweetProcessor processor2 = null;

			if (reader2Extension.equals("csv")) {
				processor2 = new TweetProcessor(reader2);
			} else {
				// second argument must be a JSON or txt file
				throw new IllegalArgumentException("Invalid, the second input file: " + reader2.getFileName() + " is not csv");
			}
			// initiate program 
			CommandLineUserInterface ui = new CommandLineUserInterface(processor1, processor2, logFile);
			ui.start();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}


