package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upenn.cit594.util.Tweet;

public class TxtReader extends TweetReader {

	// Constructor
	public TxtReader(String fileName) throws IOException {
		super(fileName);
	}

	// Method
	/***
	 * Reads the given Txt file containing tweets. 
	 * @return a list of Tweet objects obtained from the given Txt file
	 */
	public List<Tweet> readTxtFile() {
		String row;
		List<Tweet> linesOfFile = new ArrayList<Tweet>();

		try {
			// loop until the end of file is reached
			while ((row = this.reader.readLine()) != null) {

				// split tweet into location, identifier, time, and text 
				// txt file contains all identifier objects, split into 4 fields
				String [] tweetItems = row.split("\t", 4);

				// create new Tweet object and give it all four values
				Tweet strTweet = new Tweet(tweetItems[0], Integer.valueOf(tweetItems[1]), tweetItems[2], tweetItems[3]);

				// add line to existing list of Tweet objects
				linesOfFile.add(strTweet);

			}
		} catch (IOException e) {
			// prints the error message and info about which line
			e.printStackTrace();
		} finally {

			// close file
			try {
				this.reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return linesOfFile;
	}

}
