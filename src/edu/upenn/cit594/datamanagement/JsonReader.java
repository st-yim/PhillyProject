package edu.upenn.cit594.datamanagement;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.upenn.cit594.util.Tweet;

public class JsonReader extends TweetReader {

	// Constructor
	public JsonReader(String fileName) throws IOException {
		super(fileName);
	}

	// Method
	/***
	 * Parse through the given JSON file and initializes Tweet objects
	 * for every line of the file with the location, time, and text values.
	 * @return a list of Tweet objects obtained from the given JSON file
	 */
	public List<Tweet> readJsonFile() {
		List<Tweet> linesOfFile = new ArrayList<Tweet>();

		// parsing contents of the JSON file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(this.fileName)) {
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONArray Tweet = (JSONArray) obj;

			// loop through each tweet in the file
			for (int i = 0; i < Tweet.size(); i++) {

				// convert json object to type string
				JSONObject jsonObject = (JSONObject) Tweet.get(i);
				
				String location = null;
				String time = null;
				String text = null;
				
				// check for null fields 
				if (jsonObject.get("location") != null) {
					location = jsonObject.get("location").toString();
				}
				if (jsonObject.get("time") != null) {
					time = jsonObject.get("time").toString();
				}
				if (jsonObject.get("text") != null) {
					text = jsonObject.get("text").toString();
				}
			
				// create new Tweet object and give it all four values (identifier is given an arbitrary value of 0)
				Tweet strTweet = new Tweet(location, 0, time, text);

				// add line to existing list of Tweet objects
				linesOfFile.add(strTweet);	
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		} finally {

			// close file 
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return linesOfFile;
	}
}
