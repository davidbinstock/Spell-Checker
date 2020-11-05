import java.util.*;
import java.io.*;
/**
 * This class has helps to identify words that are
 * similar to potentially misspelled words
 * 
 * @author david binstock and esme shao
 *
 */
public class WordRecommender {
	//=============================================================================================================================================
	// = INSTANCE VARIABLES 
	//=============================================================================================================================================
	private String filename;
	private File dict;
	
	//=============================================================================================================================================
	// = CONSTRUCTOR
	//=============================================================================================================================================
	public WordRecommender(String filename) {
		this.filename = filename;
		dict = new File(filename);
	}
	
	//=============================================================================================================================================
	// = METHODS
	//=============================================================================================================================================
	
	/**
	 * Given two words (as Strings) this method returns their similarity score
	 * 
	 * @param word1 - first word (as a String)
	 * @param word2 - second word (as a String)
	 * @return a double representing the similarity score for the two given words
	 */
	public double getSimilarity(String word1, String word2) {
		//word1
		ArrayList<Character> word1ArrayList=new ArrayList<>();
		ArrayList<Character> revword1ArrayList=new ArrayList<>();
		for(int i=0;i<word1.length();i++)
		{
			word1ArrayList.add(word1.charAt(i));
			revword1ArrayList.add(word1.charAt(word1.length() - i -1));
		}
		
		//word2
		ArrayList<Character> word2ArrayList=new ArrayList<>();
		ArrayList<Character> revword2ArrayList=new ArrayList<>();
		for(int i=0;i<word2.length();i++)
		{
			word2ArrayList.add(word2.charAt(i));
			revword2ArrayList.add(word2.charAt(word2.length() - i -1));
		}
		
		//first compute the shortest word length(=count of operations)
		int lenthcount = 0;
		if ( word1.length() > word2.length()) {lenthcount = word2.length();}
		else{lenthcount = word1.length();}

		//Start comparing
		int leftSimilarity = 0;
		int rightSimilarity = 0;
		for(int j=0;j<lenthcount;j++) {
			if (word1ArrayList.get(j) == word2ArrayList.get(j)) {
				leftSimilarity++;
			}
			if (revword1ArrayList.get(j) == revword2ArrayList.get(j)) {
				rightSimilarity++;
			}
		}
		double similarity = (leftSimilarity + rightSimilarity)/2.0;
		return similarity;
	}
	
	/**
	 * This method takes in a word (as a string), a tolerance (as an integer)
	 * a commonPercent (as a double), and a topN (as an integer), and returns 
	 * the topN number of words with the highest similarity that meet the 
	 * tolerance and commonPercent criteria 
	 * @param word
	 * @param tolerance
	 * @param commonPercent
	 * @param topN
	 * @return an ArrayList of length "topN" of recommended replacement words
	 */
	public ArrayList<String> getWordSuggestions(String word, int tolerance, double commonPercent, int topN){
		ArrayList<String> candidateWords = new ArrayList<String>();
		ArrayList<String> suggestedWords = new ArrayList<String>();
		// First get all the words that satisfy the tolerance and commonPercent criteria
		try {
			Scanner s = new Scanner(dict);
			String nextWord;
			boolean toleranceOK;
			boolean commonPercentOK;
			while(s.hasNext()) {
				nextWord = s.next();
				toleranceOK = nextWord.length() <= ( word.length() + tolerance ) && nextWord.length() >= ( word.length() - tolerance );
				commonPercentOK = findCommonPercent(nextWord, word) >= commonPercent;
				if(toleranceOK && commonPercentOK) {
					candidateWords.add(nextWord);
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("The dictionary file could not be found");
			e.printStackTrace();
		}
		
		// Next, find the topN words with the highest similarity
		int currMaxIndex = 0;
		boolean isNextMoreSimilar;
		int numWordsToReturn;
		
		// first check if there are enough candidate words
//		System.out.println(candidateWords);
		if(topN > candidateWords.size()) {
			numWordsToReturn = candidateWords.size();
		}
		else {
			numWordsToReturn = topN;
		}
		
		for (int n=0; n < numWordsToReturn; n++) {
			currMaxIndex=0;
//			System.out.println("---------------------------------------------------");
//			System.out.println("n: "+ n);
//			System.out.println(candidateWords);
			
			for (int i = 1; i < candidateWords.size(); i++) {
//				System.out.println("i: "+ i);
//				System.out.println("currMaxIndex: "+ currMaxIndex);
//				System.out.println(candidateWords);
				isNextMoreSimilar = getSimilarity(candidateWords.get(i),word) > getSimilarity(candidateWords.get(currMaxIndex),word);
				if(isNextMoreSimilar) {
					currMaxIndex = i;
				}
			}
			suggestedWords.add(candidateWords.get(currMaxIndex));
			candidateWords.remove(currMaxIndex);	
		}
		return suggestedWords;
	}
	
	/**
	 * Given a word (as a String) this method will return
	 * an ArrayList of all unique letters (as characters); duplicate
	 * letters appear in the array only once.
	 * @param word
	 * @return an ArrayList of chars representing the unique letters
	 */
	public ArrayList<Character> getLetters(String word) {
		ArrayList<Character> letters = new ArrayList<Character>();
		boolean inLetters;
		for(int i=0; i < word.length(); i++) {
			inLetters = false;
			for(int j=0; j < letters.size(); j++) {
				if(word.charAt(i) == letters.get(j)) {
					inLetters = true;
				}
			}
			if(!inLetters) {
				letters.add(word.charAt(i));
			}
		}
		return letters;
	}
	
	/**
	 * Given two words (as Strings) this method calculates the 
	 * common percent; that is the total number of shared letters
	 * divided by the total number of letters.
	 * @param word1
	 * @param word2
	 * @return a double between 0.0 and 1.0 representing the 
	 * percent of letters in common
	 */
	public double findCommonPercent(String word1, String word2) {
		ArrayList<Character> letters1 = getLetters(word1);
		ArrayList<Character> letters2 = getLetters(word2);
		ArrayList<Character> allLetters = getLetters(word1); // start with all letters from word1 in all letters
		ArrayList<Character> commonLetters = new ArrayList<Character>();
		
		// Find Common Letters
		for(int i=0; i < letters1.size(); i++) {
			//check for common letters
			for(int j=0; j < letters2.size(); j++) {
				if(letters1.get(i) == letters2.get(j)) {
					commonLetters.add(letters1.get(i));
				}
			}
		}
		
		// Get All Letters
		// we've already put letters from word1 into allLetters
		// now we add any letters from word 2 that are not already in there
		boolean inAll;
		for(int i=0; i < letters2.size(); i++) {
			inAll = false;
			//get new letters from 2nd word
			for(int j=0; j < allLetters.size(); j++) {
				if(letters2.get(i) == allLetters.get(j)) {
					inAll = true;
				}
			}
			if(!inAll) {
				allLetters.add(letters2.get(i));
			}
		}
		
		double commonPercent = ((double)commonLetters.size()/allLetters.size());
		return commonPercent;
	}
	
	/**
	 * Given and array of strings, this method returns a string that will display an itemized liste
	 * with each element on its own line
	 * 
	 * @param list
	 * @return output String
	 */
	public String prettyPrint(ArrayList<String> list) {
		String outputString = "";
		for(int i=0; i < list.size(); i++) {
			outputString += (i+1) + ". " + list.get(i) + "\n";
		}
		return outputString;
	}
}

