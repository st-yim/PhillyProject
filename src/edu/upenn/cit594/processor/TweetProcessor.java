package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.datamanagement.JsonReader;
import edu.upenn.cit594.datamanagement.TweetReader;
import edu.upenn.cit594.datamanagement.TxtReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.Tweet;

public class TweetProcessor {

	// Field
	protected List<String> csvFile;
	protected List<Tweet> txtFile;
	protected List<Tweet> jsonFile;

	// Constructor
	public TweetProcessor(TweetReader reader) {
		this.csvFile = reader.readCSVFile();
	}

	public TweetProcessor(TxtReader reader) {
		this.txtFile = reader.readTxtFile();
	}

	public TweetProcessor(JsonReader reader) {
		this.jsonFile = reader.readJsonFile();
	}

	// Getter
	/***
	 * 
	 * @return the CSV file as a list of strings
	 */
	public List<String> getCsvFile() {
		return this.csvFile;
	}
	
	/***
	 * 
	 * @return the Txt file as a list of tweets
	 */
	public List<Tweet> getTxtFile() {
		return this.txtFile;
	}

	/***
	 * 
	 * @return the JSON file as a list of tweets
	 */
	public List<Tweet> getJsonFile() {
		return this.jsonFile;
	}

	// Method
	
	/***
	 * Store the US States and their respective coordinates into a map.
	 * States = key, coordinates = value.
	 * @param csvFile
	 * @return a hash map of US states and coordinates
	 */
	public Map<String, String> getStateMap(List<String> csvFile) {

		// CSV file contains a list of states and coordinates
		// pattern: index 0 contains the state name
		// index 0 + 1 and 0 + 2 contains the respective coordinates
		// size of list is 153, including index 0

		// initialize map for storage
		Map<String, String> stateLocationMap = new HashMap<String, String>();

		for (int i = 0, k, d; i < csvFile.size(); i++) {

			// set variables containing coordinates
			String state = csvFile.get(i);
			k = i + 1;
			d = i + 2;
			i = i + 2;

			// add them to array list and then join them with a comma
			List<String> concStrings = Arrays.asList(csvFile.get(k), csvFile.get(d));
			String location = String.join(",", concStrings);

			stateLocationMap.put(state, location);

		}	
		return stateLocationMap;
	}

	/***
	 * Identify tweets with "flu" or "#flu" and store them in a list.
	 * @param listOfTweets
	 * @return a list of Tweet objects containing the relevant tweets
	 */
	public List<Tweet> getRelevantTweet(List<Tweet> listOfTweets) {
		List<Tweet> relevantTweets = new ArrayList<Tweet>();

		// iterate through list of tweets and add relevant tweets

		// look for " #flu[.!?] " in the file
		for (int i = 0; i < listOfTweets.size(); i++) {
			Pattern pattern = Pattern.compile(" #flu[.!?] ", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(listOfTweets.get(i).getText());
			boolean matchFound = matcher.find();
			if(matchFound) {
				relevantTweets.add(listOfTweets.get(i));
				continue;
			} 
			// look for " flu[.!?] " if "#flu" isn't in the file anymore
			Pattern pattern2 = Pattern.compile(" flu[.!?] ", Pattern.CASE_INSENSITIVE);
			Matcher matcher2 = pattern2.matcher(listOfTweets.get(i).getText());
			boolean matchFound2 = matcher2.find();
			if(matchFound2) {
				relevantTweets.add(listOfTweets.get(i));
				continue;
			} 
			// look for " flu "
			Pattern pattern3 = Pattern.compile(" flu ", Pattern.CASE_INSENSITIVE);
			Matcher matcher3 = pattern3.matcher(listOfTweets.get(i).getText());
			boolean matchFound3 = matcher3.find();
			if (matchFound3) {
				relevantTweets.add(listOfTweets.get(i));
				continue;
			} 
			// look for " #flu " only at the start of a string
			Pattern pattern4 = Pattern.compile(" #flu ", Pattern.CASE_INSENSITIVE);
			Matcher matcher4 = pattern4.matcher(listOfTweets.get(i).getText());
			boolean matchFound4 = matcher4.find();
			if (matchFound4) {
				relevantTweets.add(listOfTweets.get(i));
				continue;
			} 
			// look for "#flu " only at the start of a string
			Pattern pattern5 = Pattern.compile("^#flu ", Pattern.CASE_INSENSITIVE);
			Matcher matcher5 = pattern5.matcher(listOfTweets.get(i).getText());
			boolean matchFound5 = matcher5.find();
			if (matchFound5) {
				relevantTweets.add(listOfTweets.get(i));
				continue;
			} 
			// look for "flu " only at the start of a string
			Pattern pattern6 = Pattern.compile("^flu ", Pattern.CASE_INSENSITIVE);
			Matcher matcher6 = pattern6.matcher(listOfTweets.get(i).getText());
			boolean matchFound6 = matcher6.find();
			if (matchFound6) {
				relevantTweets.add(listOfTweets.get(i));
				continue;
			} 
			// look for "flu" only at the end of a string
			Pattern pattern7 = Pattern.compile(" flu$", Pattern.CASE_INSENSITIVE);
			Matcher matcher7 = pattern7.matcher(listOfTweets.get(i).getText());
			boolean matchFound7 = matcher7.find();
			if (matchFound7) {
				relevantTweets.add(listOfTweets.get(i));
				continue;
			} 
			// look for "flu[.!?]" only at the end of a string
			Pattern pattern8 = Pattern.compile(" flu[.!?]$", Pattern.CASE_INSENSITIVE);
			Matcher matcher8 = pattern8.matcher(listOfTweets.get(i).getText());
			boolean matchFound8 = matcher8.find();
			if (matchFound8) {
				relevantTweets.add(listOfTweets.get(i));
				continue;
			} 
			// look for "#flu[.!?]" only at the end of a string
			Pattern pattern9 = Pattern.compile(" #flu[.!?]$", Pattern.CASE_INSENSITIVE);
			Matcher matcher9 = pattern9.matcher(listOfTweets.get(i).getText());
			boolean matchFound9 = matcher9.find();
			if (matchFound9) {
				relevantTweets.add(listOfTweets.get(i));
				continue;
			} 
			// look for "#flu[.!?]" only at the end of a string
			Pattern pattern10 = Pattern.compile(" #flu$", Pattern.CASE_INSENSITIVE);
			Matcher matcher10 = pattern10.matcher(listOfTweets.get(i).getText());
			boolean matchFound10 = matcher10.find();
			if (matchFound10) {
				relevantTweets.add(listOfTweets.get(i));
				continue;
			} 
			else {
				continue;
			}
		}  
		return relevantTweets;
	}

	/***
	 * Calculate each relevant tweet's state of origin by calculating their distance with respect
	 * to all states in the given CSV file.
	 * @param stateMap
	 * @param relevantTweets
	 * @return a list of strings containing the relevant tweets' states of origin
	 */
	public List<String> getLocationOfTweet(Map<String, String> stateMap, List<Tweet> relevantTweets) {
		List<String> keyTracker = new ArrayList<String>();
		
		// iterate over list of tweets 
		for (int i = 0; i < relevantTweets.size(); i++) {
			
			// split tweet coordinates by longitude and latitude
			LinkedList<String> distanceStack = new LinkedList<String>(); 
			
			String coordinate = relevantTweets.get(i).getLocation();

			String[] tweetCoordinates = coordinate.replaceAll("^\\[|\\]$", "").split(",", 2);
			
			String key = null;

			// iterate over given map 
			for (Map.Entry<String, String> entry : stateMap.entrySet()) {

				// split state coordinates by longitude and latitude
				String value = entry.getValue();
				String[] stateCoordinates = value.split(",", 2);
	
				// calculate distance:

				// (longitude2 - longitude1)^2
				double longitude2 =  Double.valueOf(tweetCoordinates[0]);
				double longitude1 = Double.valueOf(stateCoordinates[0]);
				double longitude = longitude2 - longitude1;
				double longitudeSquared = longitude*longitude;

				// (latitude2 - latitude1)^2
				double latitude2 =  Double.valueOf(tweetCoordinates[1]);
				double latitude1 = Double.valueOf(stateCoordinates[1]);
				double latitude = latitude2 - latitude1;
				double latitudeSquared = latitude*latitude;

				// distance = sqrt[(longitude2 - longitude1)^2 + (latitude2 - latitude1)^2]
				double sum = longitudeSquared + latitudeSquared;
				double distance = Math.sqrt(sum);

				// convert distance to string and push to stack
				String distString = String.valueOf(distance);
				distanceStack.push(distString);

				// check if stack has two elements, continue w/o comparisons made below if FALSE
				if (!hasTwoElements(distanceStack)) {
					key = entry.getKey();
					continue;
				}
				
				// check if top stack (what we just added) is >, <, or = previous top stack
				double topOfStack = Double.valueOf(distanceStack.pop());

				// set double to previous top stack
				double nextOnStack = Double.valueOf(distanceStack.pop());
				int compare = Double.compare(topOfStack, nextOnStack);

				// compare top stack's distance to previous top's
				// ADD previous top to the stack if the current top distance > previous top distance
				if (compare > 0) {
					distanceStack.push(String.valueOf(nextOnStack));
					// else current top stack is SMALLEST in distance, set key to current top
				} else if (compare < 0) {
					distanceStack.push(String.valueOf(topOfStack));
					key = entry.getKey();
					// current top stack is EQUAL in distance to previous, set key to current top
				} else {
					distanceStack.push(String.valueOf(topOfStack));
					key = entry.getKey();
				}
			}
			
			// add key to key tracker list 
			keyTracker.add(key);

			// log state and respective text
			Logger logger = Logger.getInstance();
			logger.log(key + "\t" + relevantTweets.get(i).getText());
		}
		return keyTracker;
	}
	
	/***
	 * Helper method: Calculate if the given stack has two elements.
	 * @param stack
	 * @return true if the stack has two elements, else false
	 */
	public static boolean hasTwoElements(LinkedList<String> stack) {

		if (stack.isEmpty())
			return false;

		// pop top of stack
		String top = stack.pop();

		// true if stack isn't empty, false if stack is empty
		boolean hasTwo = !stack.isEmpty();

		stack.push(top);
		return hasTwo;
	}

	/***
	 * Put the states with "flu tweets" into a map and increment their value based on the number of associated tweets.
	 * @param fluStateList
	 * @return a tree map of all states with "flu tweets"
	 */
	public Map<String, Integer> getFluState(List<String> fluStateList) {
		Map<String, Integer> fluStateMap = new TreeMap<String, Integer>();

		// iterate over list of states with flu tweets
		for(int i = 0; i < fluStateList.size(); i++) {

			// doesn't exist: add state as key with value 1 
			if(!fluStateMap.containsKey(fluStateList.get(i))){
				fluStateMap.put(fluStateList.get(i), 1);
			} else {
				// exists: increment value by 1
				fluStateMap.put(fluStateList.get(i), fluStateMap.get(fluStateList.get(i)) + 1);
			}
		}
		return fluStateMap;
	}
}
