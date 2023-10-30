package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.upenn.cit594.util.Tweet;

class TxtReaderTest {

	@Test
	void testReadTxtFile() {
		try(var reader = new TxtReader("flu_tweets.txt")){ 

			// initialize list, print file
			List<Tweet> tweetRList = new ArrayList<Tweet>();
			tweetRList = reader.readTxtFile();
			System.out.println(tweetRList);

		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
