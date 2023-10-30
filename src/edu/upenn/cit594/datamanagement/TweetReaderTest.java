package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class TweetReaderTest {

	@Test
	void testReadCsvFile() {
		try(var reader = new TweetReader("states.csv")){ 

			// initialize list, print file
			List<String> tweetRList = new ArrayList<String>();
			tweetRList = reader.readCSVFile();
			System.out.println(tweetRList);

		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	@Test
	void testGetFileExtension() {

		// get json extension
		try(var reader = new TweetReader("flu_tweets.json")){ 
			String extensionString = reader.getFileExtension(); 
			System.out.println(extensionString);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		// get txt extension
		try(var reader = new TweetReader("flu_tweets.txt")){ 
			String extensionString = reader.getFileExtension(); 
			System.out.println(extensionString);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		// get csv extension
		try(var reader = new TweetReader("states.csv")){ 
			String extensionString = reader.getFileExtension(); 
			System.out.println(extensionString);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}
}
