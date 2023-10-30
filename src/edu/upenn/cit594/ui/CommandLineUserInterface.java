package edu.upenn.cit594.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import edu.upenn.cit594.processor.TweetProcessor;
import edu.upenn.cit594.util.Tweet;


public class CommandLineUserInterface {

	// Field
	protected TweetProcessor tweetProcessor1;
	protected TweetProcessor tweetProcessor2;
	protected String logFile; 

	// Constructor
	public CommandLineUserInterface(TweetProcessor processor1, TweetProcessor processor2, String logFile) {
		this.tweetProcessor1 = processor1;
		this.tweetProcessor2 = processor2;
		this.logFile = logFile;
		
	}
	
	// Method
	public void start() {
		doStatesWithFluTweet();
	}
	/***
	 * Given three arguments: tweet file, US States and coordinates file, and log file,
	 * calculate the relevant tweets and their respective state of origin.
	 * Then, print out relevant US States with their "flu tweets"
	 */
	protected void doStatesWithFluTweet() {
		
		// get state file
		Map<String, String> stateLocationMap = new HashMap<String, String>();
		stateLocationMap = tweetProcessor2.getStateMap(tweetProcessor2.getCsvFile());

		// get tweet file
		List<Tweet> tweetFile = new ArrayList<Tweet>();

		if (tweetProcessor1.getJsonFile() != null) {
			tweetFile = tweetProcessor1.getJsonFile();
		} else {
			tweetFile = tweetProcessor1.getTxtFile();
		}
		// get relevant tweets
		List<Tweet> relevantTweets = new ArrayList<Tweet>();
		relevantTweets = tweetProcessor1.getRelevantTweet(tweetFile);

		// calculate distance and location
		List<String> distance = new ArrayList<String>();
		distance = tweetProcessor1.getLocationOfTweet(stateLocationMap, relevantTweets);

		// get states with >= 1 flu tweet
		Map<String, Integer> fluStateMap = new TreeMap<String, Integer>();
		fluStateMap = tweetProcessor1.getFluState(distance);
		
		// print formatted output
		for(Entry<String, Integer> entry : fluStateMap.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

	}
}

