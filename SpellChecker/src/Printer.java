import java.io.*;
public class Printer {
	
	private String outputFileName;
	
	public Printer(String filename) {
		outputFileName = filename;
	}
	
	/**
	 * Given a word as a String (actually any String) this
	 * method will append it to the text file.
	 * @param word The word (or String) to append
	 */
	public void appendWord(String word) {
		try {
			FileWriter fw = new FileWriter(outputFileName, true); //added this in order to get words to append
			PrintWriter pw = new PrintWriter(fw);
			pw.print(word + " ");
			pw.flush();
		} catch (FileNotFoundException e) {
			System.out.println("something went wrong with the filename");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will clear the contents of the output file.
	 * If the output file has not yet been created, this method
	 * will also create the file.
	 */
	public void clearFile() {
		try {
			PrintWriter pw = new PrintWriter(outputFileName);
			pw.print("");
			pw.flush();
		} catch (FileNotFoundException e) {
			System.out.println("something went wrong with the filename");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method returns the string value of the name 
	 * of the output text file 
	 * @return the output file name as a String
	 */
	public String getFilename() {
		return outputFileName;
	}
		
	public static void main(String[] args) {
		Printer p = new Printer("testout.txt");
		p.clearFile();
		p.appendWord("hello");
		p.appendWord("what's");
		p.appendWord("the");
		p.appendWord("Skinny?");

	}

}
