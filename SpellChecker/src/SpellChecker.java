import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class SpellChecker {
	private Scanner scnr;
	private String fileName;
	private Reader fileReader;
	private Printer printer;
	private String dictionaryFileName = "engDictionary.txt";
	
	public SpellChecker() {
		scnr = new Scanner(System.in);
	}
	
	private void getInputFileReader() {
		System.out.println("Hello. Please enter the name of the file you would like to spell check (e.g. myfile.txt)");
		//get input file
		boolean success = false;
		while(!success) {
			fileName = scnr.next();
			try {
				fileReader = new Reader(fileName);
				success = true;
			} catch (FileNotFoundException e) {
				System.out.println("Filename not found please enter a new file name: ");
			}
		}
	}
	
	private void initializePrinter() {
		printer = new Printer("testout.txt");
		printer.clearFile();
	}
	
//	public String generateOutputFileName(String inputFileName) {
//		inputFileName.split(".");
//	}
	
	public boolean isWordInDictionary(String word) {
		File dict = new File(dictionaryFileName);
		try {
			scnr = new Scanner(dict);
			String nextWord;
			while(scnr.hasNext()) {
				nextWord = scnr.next();
				if(word.equals(nextWord)) {
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("The dictionary file could not be found");
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String getUserWordChoice(String currentWord, ArrayList<String> suggestedWords, boolean hasSuggestions) {
		Scanner s_action = new Scanner(System.in);
		String finalWord = "";
		String response;
		boolean done = false;
		
		if(!hasSuggestions) {
			System.out.println("Press 'a' for accept as is, 't' for type in manually");
		}else {
			System.out.println("Press 'r' for replace, 'a' for accept as is, 't' for type in manually");
		}
		
		while(!done) {
			response = s_action.next();
			if(response.equals("r") && hasSuggestions) {
				System.out.println("Your word will now be replaced with one of the suggestions");
				System.out.println("Enter the number corresponding to the word that you want to use for replacement.");
				int wordNum;
				boolean isValid = false;
				while(!isValid) {
					wordNum = s_action.nextInt();
					if(wordNum <= suggestedWords.size() && wordNum >= 1) {
						isValid = true;
						finalWord = suggestedWords.get(wordNum-1);
					}else {
						System.out.println("Invalid entry: please select a valid number for the replacement word");
					}
				}
				done = true;
				
			}
			else if(response.equals("a")) {
				finalWord = currentWord;
				done = true;
			}
			else if(response.equals("t")) {
				System.out.println("Please type the word that will be used as the replacement in the output file");
				finalWord = s_action.next();
				done = true;
			}else {
				if(hasSuggestions) {
					System.out.println("Invalid response. Please press 'r' for replace, 'a' for accept as is, 't' for type in manually");
				}
				else {
					System.out.println("Invalid response. Please press 'a' for accept as is, 't' for type in manually");
				}
			}
		}
		
		return finalWord;
	}
	
	public void run() {
		
		getInputFileReader();
		System.out.println("--input file accepted--");
		
		initializePrinter();
		System.out.println("--printer initialized--");
		
		WordRecommender wordRec = new WordRecommender(dictionaryFileName);
		
		String currWord;
		ArrayList suggestedWords;
		boolean isSpelledCorrectly;
		String wordChoice;
		while(fileReader.hasNextWord()) {
			
			currWord = fileReader.nextWord();
//			System.out.print(currWord + " : in dictionary -- "); 	//FIXME delete; for testing purposes
			isSpelledCorrectly = isWordInDictionary(currWord);
//			System.out.println(isSpelledCorrectly); 				//FIXME delete; for testing purposes
			
			if(isSpelledCorrectly) {
				printer.appendWord(currWord);
			}
			else {
				suggestedWords = wordRec.getWordSuggestions(currWord, 1, 0.75, 3);
				boolean hasSuggestions;
				if(suggestedWords.size() == 0) {
					System.out.println("The word '" + currWord + "' is misspelled.");
					System.out.println("There are 0 suggestions in our dictionary for this word");
					hasSuggestions = false;
					
				}else {
					System.out.println("The word '" + currWord + "' is misspelled. The following suggestions are avaialable:");
					System.out.println(wordRec.prettyPrint(suggestedWords));
					hasSuggestions = true;
				}
				wordChoice = getUserWordChoice(currWord, suggestedWords, hasSuggestions);
				printer.appendWord(wordChoice);
			}
		}
	}
	
	public static void main(String[] args) {
		SpellChecker spch = new SpellChecker();
//		spch.run();
		
	}

}
