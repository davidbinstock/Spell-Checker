import java.io.*;
public class Printer {
	
	private String outputFileName;
	
	public Printer(String filename) {
		outputFileName = filename;
	}
	
	public void printNextWord(String word) {
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
		
	public static void main(String[] args) {
		Printer p = new Printer("testout.txt");
		p.clearFile();
		p.printNextWord("hello");
		p.printNextWord("what's");
		p.printNextWord("the");
		p.printNextWord("Skinny?");

	}

}
