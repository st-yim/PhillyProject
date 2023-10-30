package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


enum File {CSV, JSON, TXT};

public class TweetReader implements AutoCloseable {

	// Field
	protected BufferedReader reader;
	protected String fileName;

	// Constructor
	public TweetReader(String fileName) throws IOException {
		this.fileName = fileName;
		this.reader = Files.newBufferedReader(Paths.get(this.fileName));
	}

	// Getter
	/***
	 * 
	 * @return the file name as a string
	 */
	public String getFileName() {
		return this.fileName;
	}

	/***
	 * 
	 * @return the file's extension
	 */
	public String getFileExtension() {
		String fileExtension = null;

		int index = fileName.lastIndexOf('.');

		// gets extension of given file
		if(index > 0) {
			fileExtension = fileName.substring(index + 1);
		}

		// call isInEnum method to determine if code supports the format
		boolean isValidFile = isInEnum(fileExtension, File.class);

		// throw exception if format is not supported
		if (!isValidFile) {
			throw new UnsupportedOperationException("The file: " + this.fileName + " is not supported, sorry");
		}

		return fileExtension;
	}

	// Method
	/***
	 * Gets the file line as a string.
	 * @return a single line of text from the given TweetReader object's reader
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		return this.reader.readLine();
	}

	/***
	 * Reads the given CSV file containing US states and their coordinates. 
	 * @return a list of String objects consisting of the 50 US States + W.D.C and their locations
	 */
	public List<String> readCSVFile() {
		List<String> linesOfFile = new ArrayList<String>();

		try {
			for(String line = this.reader.readLine(); line != null; line = this.reader.readLine()){

				// split line into array of 3 values: state, longitude, and latitude in that order
				String [] stateAndLocations = line.split(",", 3);

				// add the 3 strings to the list
				linesOfFile.add(stateAndLocations[0]);
				linesOfFile.add(stateAndLocations[1]);
				linesOfFile.add(stateAndLocations[2]);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
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
	
	/***
	 * Helper method: Check if our file is supported by comparing its extension to our extensions.
	 * @param <E>
	 * @param fileExtension
	 * @param enumClass
	 * @return true if the given file's extension is in our enum, else false
	 */
	public <E extends Enum<E>> boolean isInEnum(String fileExtension, Class<E> enumClass) {
		// iterate through enum constants
		for (E e : enumClass.getEnumConstants()) {
			if(e.name().equals(fileExtension.toUpperCase())) { 
				return true; 
			}
		}
		return false;
	}
	
	@Override
	public void close() throws IOException {
		this.reader.close();
	}

}
