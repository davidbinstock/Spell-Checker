import java.io.*;
import java.util.*;

public class Reader {
	private String inputFileName;
	private File inputFile;
	private Scanner s;
	
	public Reader(String filename) throws FileNotFoundException { // throwing the exceptoion to be handled by the method that calls this
		inputFileName = filename;
		inputFile = new File(inputFileName);
		s = new Scanner(inputFile);		
	}

	/**
	 * This method returns the next word in the file that is being 
	 * spell-checked
	 * 
	 * @return the next word in the file (as a String)
	 */
	public String nextWord() {
		if(s.hasNext()) {
			return s.next();
		}
		else {
			return "END_OF_INPUT_FILE";
		}
	}
	
	/**
	 * This method determines if there is a next word in
	 * the file that is being spell-checked and returns
	 * true or false accordingly
	 * 
	 * @return a boolean true or false
	 */
	public boolean hasNextWord() {
		return s.hasNext();
	}
	
}
