package edu.upenn.cit594.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import edu.upenn.cit594.datamanagement.JsonReader;
import edu.upenn.cit594.datamanagement.TweetReader;
import edu.upenn.cit594.datamanagement.TxtReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.Tweet;

public class TweetProcessorTest {

	@Test
	void testGetStateMap() {
		try(var reader = new TweetReader("states.csv")){  
			var TweetProcessor = new TweetProcessor(reader);

			// initialize map 
			Map<String, String> stateLocationMap = new HashMap<String, String>();
			stateLocationMap = TweetProcessor.getStateMap(TweetProcessor.getCsvFile());
			System.out.println(stateLocationMap);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	void testGetRelevantTweet() {
		try(var reader = new JsonReader("flu_tweets.json")){ 
			var TweetProcessor = new TweetProcessor(reader);

			List<Tweet> relevantTweets = new ArrayList<Tweet>();
			relevantTweets = TweetProcessor.getRelevantTweet(TweetProcessor.getJsonFile());

			for (int i = 0; i < relevantTweets.size(); i++) {
				System.out.println(relevantTweets.get(i).getText());
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		try(var reader = new TxtReader("flu_tweets.txt")){  
			var TweetProcessor = new TweetProcessor(reader);

			List<Tweet> relevantTweets = new ArrayList<Tweet>();
			relevantTweets = TweetProcessor.getRelevantTweet(TweetProcessor.getTxtFile());

			for (int i = 0; i < relevantTweets.size(); i++) {
				System.out.println(relevantTweets.get(i).getText());
				System.out.println(relevantTweets.get(i).getLocation());
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		try(var reader = new JsonReader("trivial_tweets.json")){ 
			var TweetProcessor = new TweetProcessor(reader);

			List<Tweet> relevantTweets = new ArrayList<Tweet>();
			relevantTweets = TweetProcessor.getRelevantTweet(TweetProcessor.getJsonFile());

			for (int i = 0; i < relevantTweets.size(); i++) {
				System.out.println(relevantTweets.get(i).getText());
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}
	public static boolean hasTwoElements(LinkedList<String> stack) {
		if (stack.isEmpty())
			return false;
		String top = stack.pop();
		boolean hasTwo = ! stack.isEmpty();
		stack.push(top);
		return hasTwo;
	}

	@Test
	void testGetLocationOfTweet() {
		try(var reader = new JsonReader("flu_tweets.json")){ 
			var TweetProcessor = new TweetProcessor(reader);

			TweetReader reader1 = new TweetReader("states.csv");
			TweetProcessor TweetProcessor1 = new TweetProcessor(reader1);

			Logger logger = Logger.getInstance();
			logger.setOutput("log.txt");
			
			// initialize map 
			// get state map
			Map<String, String> stateLocationMap = new HashMap<String, String>();
			stateLocationMap = TweetProcessor1.getStateMap(TweetProcessor1.getCsvFile());

			// initialize list
			// get relevant tweets
			List<Tweet> relevantTweets = new ArrayList<Tweet>();
			relevantTweets = TweetProcessor.getRelevantTweet(TweetProcessor.getJsonFile());
			System.out.println(relevantTweets.size());

			// initialize list
			// get location of tweet
			List<String> tweetLocation = new ArrayList<String>();
			tweetLocation = TweetProcessor.getLocationOfTweet(stateLocationMap, relevantTweets);
			System.out.println(tweetLocation);

			Map<String, Integer> fluStateMap = new TreeMap<String, Integer>();
			fluStateMap = TweetProcessor.getFluState(tweetLocation);
			System.out.println(fluStateMap);

			// print formatted output
			for(Entry<String, Integer> entry : fluStateMap.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
