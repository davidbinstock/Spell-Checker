import java.io.*;
import java.util.*;

public class Reader {
	private String inputFileName;
	private File inputFile;
	private Scanner s;
	
	public Reader(String filename) throws FileNotFoundException {
		inputFileName = filename;
		inputFile = new File(inputFileName);
		s = new Scanner(inputFile);		
	}
	
//	private void initializeScanner(File f) {
//		
//	}
	
//	public boolean setFile() {
//		
//	}
	
	public String nextWord() {
		if(s.hasNext()) {
			return s.next();
		}
		else {
			return "END_OF_INPUT_FILE";
		}
	}
	
//	public void test() {
//		for (int i=0; i<10; i++) {
//			System.out.println(s.next());
//		}
//	}
	
	public boolean hasNextWord() {
		return s.hasNext();
	}
	
	
	public static void main(String[] args) {
//		
//		Reader r;
//		try {
//			r = new Reader("test.txt");
//			//System.out.println("all good");
//			//r.test();
//		} catch (FileNotFoundException e) {
//			System.out.println("please enter a valid filename");
//		}
	}

}
