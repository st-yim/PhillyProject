package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger implements AutoCloseable {

	// Field
	private static Logger instance = null;
	private PrintWriter out;
	FileWriter fileWriter;

	// Constructor
	private Logger() {}
	
	// Method
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}

	/***
	 * Log the given US State and the corresponding text depending on the calculated distance.
	 * Flush the print writer afterwards.
	 * @param msg
	 */
	public void log(String msg) {
		out.println(msg);
		out.flush();
	}
	
	/***
	 * Create a new file or turn on append mode to an existing file given the inputted file's name.
	 * @param fileName
	 */
	public void setOutput(String fileName) {
		try { 
			File f = new File(fileName);
			// doesn't exist, create file 
			if (!f.isFile()) {
				f.createNewFile();
				fileWriter = new FileWriter(f);
				out = new PrintWriter(fileWriter);
			} else {
				// turn on append mode
				fileWriter = new FileWriter(f, true);
				out = new PrintWriter(fileWriter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void close() throws IOException {
		this.out.close();
	}

}
