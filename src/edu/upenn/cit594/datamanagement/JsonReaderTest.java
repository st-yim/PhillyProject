package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.upenn.cit594.util.Tweet;

class JsonReaderTest {

	@Test
	void testReadJsonFile() {
		try(var reader = new JsonReader("flu_tweets.json")){ 

			// initialize list, print file
			List<Tweet> tweetRList = new ArrayList<Tweet>();
			tweetRList = reader.readJsonFile();
			System.out.println(tweetRList);

		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
