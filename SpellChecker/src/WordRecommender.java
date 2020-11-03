import java.util.*;
import java.io.*;

public class WordRecommender {
	private String filename;
	private File dict;
	
	public WordRecommender(String filename) {
		this.filename = filename;
		dict = new File(filename);
	}
		
	public double getSimilarity(String word1, String word2) {
		//word1
		ArrayList<Character> word1ArrayList=new ArrayList<>();
		ArrayList<Character> revword1ArrayList=new ArrayList<>();
		for(int i=0;i<word1.length();i++)
		{
			word1ArrayList.add(word1.charAt(i));
			revword1ArrayList.add(word1.charAt(word1.length() - i -1));
		}
		
		//test if working, delete after
//		System.out.println(word1ArrayList);
//		System.out.println(revword1ArrayList);
		
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
		
		//return the figure, fix later
		//print for testing, delete later
//		System.out.println("leftSimilarity: " + leftSimilarity);
//		System.out.println("rightSimilarity: " + rightSimilarity);
//		System.out.println("Similarity Score: " + similarity);
		return similarity;
		
	}
	
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
		// not we had any letters from word 2 that are not already in there
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
		
//		System.out.println(commonLetters);
//		System.out.println(allLetters);
//		System.out.println(commonLetters.size());
//		System.out.println(allLetters.size());
		double commonPercent = ((double)commonLetters.size()/allLetters.size());
//		System.out.println(commonPercent);
		return commonPercent;
	}
	
	
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
		
		//FIXME delete; for testing purposes
//		System.out.println(candidateWords);
//		for (int i = 1; i<suggestedWords.size(); i++) {
//			System.out.print("i = " + i + ": ");
//			System.out.print(getSimilarity(suggestedWords.get(i),word));
//			System.out.println(suggestedWords.get(i));
//		}
		
		
		
		//Next, find the topN words with the highest similarity
		int currMaxIndex = 0;
		boolean isNextMoreSimilar;
		int numWordsToReturn;
		// first check if there are enough candidate words
		if(topN > candidateWords.size()) {
			numWordsToReturn = candidateWords.size();
		}
		else {
			numWordsToReturn = topN;
		}
		
		
		for (int n=0; n < numWordsToReturn; n++) {
			for (int i = 1; i<candidateWords.size(); i++) {
				isNextMoreSimilar = getSimilarity(candidateWords.get(i),word) > getSimilarity(candidateWords.get(currMaxIndex),word);
				if(isNextMoreSimilar) {
					currMaxIndex = i;
				}
			}
//			System.out.println("max similarity at: " + currMaxIndex); //FIXME delete; for testing purposes
			suggestedWords.add(candidateWords.get(currMaxIndex));
			candidateWords.remove(currMaxIndex);	
		}
		System.out.println(suggestedWords); //FIXME delete; for testing purposes
		return suggestedWords;
	}
	
	public static void main(String[] args) {
		WordRecommender wordRec = new WordRecommender("engDictionary.txt");
		wordRec.getWordSuggestions("hair", 2, 0.75, 3);
		wordRec.getWordSuggestions("ewok", 1, 1.5, 3);
		//wordRec.getSimilarity("oblige", "oblivion");
	}




}

