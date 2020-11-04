import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
 * 
 * This is the main class for the project. It owns instances of the other classes (besides the runner) and contains
 * the central run() method which implements the code and coordinates the activities of the program. 
 * 
 * @author david binstock and esme shao
 *
 */
public class SpellChecker {
	//=============================================================================================================================================
	// = INSTANCE VARIABLES 
	//=============================================================================================================================================
	private Scanner scnr;										// scanner used for user input
	private String dictionaryFileName = "engDictionary.txt";	// hard-coded dictionary file name: change here to switch dictionary files
	private String inputFileName;								// input file name
	private String outputFileName;								// output file name
	private File dictionaryFile;								// instance of the File class created with the dictionaryFileName given
	private Reader fileReader;									// instance of the project Reader class
	private Printer printer;									// instance of the project Printer class
	private WordRecommender wordRec;							// instance of the project WordRecommender class
	
	//=============================================================================================================================================
	// = CONSTRUCTOR
	//=============================================================================================================================================
	public SpellChecker() {
		scnr = new Scanner(System.in);
		dictionaryFile = new File(dictionaryFileName);
		wordRec = new WordRecommender(dictionaryFileName);
	}
	
	
	//=============================================================================================================================================
	// = RUN METHOD
	//=============================================================================================================================================
	/**
	 * This is the method which begins the spell check program
	 */
	public void run() {
		// prompt the user for input file name and validate it
		getInputFileReader();
		System.out.println("--input file accepted--");
		
		// create and instance of the printer class which will take care of the output
		initializePrinter();
		System.out.println("--printer initialized--");
		
		
		
		String currWord;
		ArrayList suggestedWords;
		boolean isSpelledCorrectly;
		String wordChoice;
		while(fileReader.hasNextWord()) {
			
			currWord = fileReader.nextWord();
			isSpelledCorrectly = isWordInDictionary(currWord);
			
			if(isSpelledCorrectly) {
				printer.appendWord(currWord);
			}
			else {
				suggestedWords = wordRec.getWordSuggestions(currWord, 1, 0.75, 4);
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
		
		// inform user the program has finished and print out the output file name
		System.out.println("The spell check program has finished. The updated spell-checked file is: ");
		System.out.println(printer.getFilename());
	}
	

	//=============================================================================================================================================
	// = ADDITIONAL METHODS
	//=============================================================================================================================================
	/**
	 * This method prompts the user for the name of the file to be spellchecked
	 * if the file name is not valid it will keep re-prompting the user until a
	 * valid filename is entered.
	 */
	private void getInputFileReader() {
		System.out.println("Hello. Please enter the name of the file you would like to spell check (e.g. myfile.txt)");
		//get input file
		boolean success = false;
		while(!success) {
			inputFileName = scnr.next();
			try {
				fileReader = new Reader(inputFileName);
				outputFileName = generateOutputFileName(inputFileName);
				success = true;
			} catch (FileNotFoundException e) {
				System.out.println("Filename not found please enter a new file name: ");
			}
		}
	}
	
	/**
	 * Given the name of an input file (as a String) this method generates the
	 * output file name
	 * 
	 * @param inputFileName - the name of the input file (as a String)
	 * @return output file name (as a String)
	 */
	public String generateOutputFileName(String inputFileName) {
		String[] nameArray = inputFileName.split("\\.");
		String outputFileName = nameArray[0] + "_chk" + "." + nameArray[1];
		return outputFileName;
	}
	
	/**
	 * This method creates an instance of the Printer class using the output
	 * filename, which is generated based on the input name
	 */
	private void initializePrinter() {
		printer = new Printer(outputFileName);
		printer.clearFile();
	}
	

	/**
	 * Given a word (as a String), this method determines whether or not the 
	 * word is in the dictionary and returns true or false accordingly
	 * 
	 * @param word - the word to be checked (as a String)
	 * @return boolean true (if the word is in the dictionary) false (if
	 * the word is not in the dictionary)
	 */
	public boolean isWordInDictionary(String word) {
		// try catch in case the dictionary file cannot be found
		try {
			Scanner dictionaryScnr = new Scanner(dictionaryFile);	// Create a new scanner that will scan the dictionary file 
			String nextWord;										
			while(dictionaryScnr.hasNext()) {						// loop through all entries in the dictionary
				nextWord = dictionaryScnr.next();					
				if(word.equals(nextWord)) {							// if the given word matches a word in the dictionary 
					return true;									// return true
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("The dictionary file could not be found");
			e.printStackTrace();
		}
		
		return false;												// otherwise return false
	}
	
	
	/**
	 * This is a private method which handles the user's input when a misspelled word is found.
	 * It asks the user if they would like to replace the word with one of the suggestions( "r"),
	 * accept the word as is ("a"), or type out the correction manually ("t"). It then returns the
	 * final word chosen/given by the user.
	 * 
	 * @param currentWord
	 * @param suggestedWords
	 * @param hasSuggestions
	 * @return
	 */
	private String getUserWordChoice(String currentWord, ArrayList<String> suggestedWords, boolean hasSuggestions) {
		String finalWord = "";
		String response;
		boolean done = false;
		
		// The prompt message will depend on whether or not suggested replacement words were found
		if(!hasSuggestions) {																			// if the word does NOT have suggestions
			System.out.println("Press 'a' for accept as is, 't' for type in manually");					// options are only "a" and "t"
		}else {																							// otherwise the word has suggestions
			System.out.println("Press 'r' for replace, 'a' for accept as is, 't' for type in manually");// options are "r", "a", and "t"
		}
		
		// processing and validating the user's input
		// done will be set to "true" when only when a valid input is received
		while(!done) { 
			response = scnr.next();															// get user response
			
			//----------------------------------------------------------------------------
			// if entry is "r" 
			// user chooses to replace the word with one of the suggestions
			// (note: entry "r" is only valid if the word has suggestions)
			//----------------------------------------------------------------------------
			if(response.equals("r") && hasSuggestions) {										
				System.out.println("Your word will now be replaced with one of the suggestions");
				System.out.println("Enter the number corresponding to the word that you want to use for replacement.");
				
				// process and validate the number entered by user
				int wordNum;																
				boolean isValid = false;													
				while(!isValid) {															// loop until valid entry is given		
					wordNum = scnr.nextInt();												// get number entry
					if(wordNum <= suggestedWords.size() && wordNum >= 1) {					// check if it's in bounds
						isValid = true;														// if yes, then set isValid to true (to end loop)
						finalWord = suggestedWords.get(wordNum-1);							// the final word is the one corresponding to the chosen number
					}else {																	// Otherwise the entry is invalid - re-prompt
						System.out.println("Invalid entry: please select a valid number for the replacement word");
					}
				}
				done = true;																// valid response so done is true
				
			}
			//----------------------------------------------------------------------------
			// if entry is "a" 
			// user accepts word as is
			//----------------------------------------------------------------------------
			else if(response.equals("a")) {																
				finalWord = currentWord;													// user accepts word as is, and finalWord is currentWord 
				done = true;																// valid response so done is true
			}
			//----------------------------------------------------------------------------
			// if entry is "t" 
			// user enters a new word manually
			//----------------------------------------------------------------------------
			else if(response.equals("t")) {													
				System.out.println("Please type the word that will be used as the replacement in the output file");
				finalWord = scnr.next();													// get new word from user
				done = true;																// valid response so done is true
				
			//----------------------------------------------------------------------------
			// else entry was invalid. Re-prompt user for entry. 
			// again, specific prompt depends on whether word has suggestions or not 
			//----------------------------------------------------------------------------	
			}else {
				if(hasSuggestions) {
					System.out.println("Invalid response. Please press 'r' for replace, 'a' for accept as is, 't' for type in manually");
				}
				else {
					System.out.println("Invalid response. Please press 'a' for accept as is, 't' for type in manually");
				}
			}
		}
		// while loop will continue until a valid entry is chosen which will determine the final word choice
		// once loop finishes, return this word
		return finalWord;
	}
}
