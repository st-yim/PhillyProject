package edu.upenn.cit594.util;

public class Tweet {
	
	// Field
	private final String location;
	private final String text;
	private final String date;
	private final int identifier;

	// Constructor
	public Tweet(String location, int identifier, String date, String text) {
		this.location = location;
		this.identifier = identifier;
		this.date = date;
		this.text = text;
	}

	// Getter
	/***
	 * 
	 * @return location of the Tweet object
	 */
	public String getLocation() {
		return location;
	}

	/***
	 * 
	 * @return identifier of the Tweet object
	 */
	public int getIdentifier() {
		return identifier;
	}

	/***
	 * 
	 * @return date of the Tweet object
	 */
	public String getDate() {
		return date;
	}

	/***
	 * 
	 * @return text of the Tweet object
	 */
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return "Tweet{" +
				"location=" + location +
				", identifier='" + identifier +
				", date=" + date + 
				", text=" + text + '\'' +
				'}';
	}
}
