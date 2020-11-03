import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WordRecommenderTest {
	
	WordRecommender wordRec = new WordRecommender("engDictionary.txt");
	
	@BeforeEach
	void setUp() throws Exception {
		
	}

	@Test
	void testGetSimilarity() {
		double outputSim1 = wordRec.getSimilarity("oblige", "oblivion");
		assertEquals(2.5, outputSim1);
	}
	@Test
	void testGetSimilarityNone() {
		double outputSim1 = wordRec.getSimilarity("zebra", "fox");
		assertEquals(0, outputSim1);
	}

	@Test
	void testGetWordSuggestions() {
		int expectedLength1 = 3;
		assertEquals(expectedLength1, wordRec.getWordSuggestions("hairr", 2, 0.75, 3).size());
	}
	
	@Test
	void testGetWordSuggestionsCorrectSpelling() {
		ArrayList<String> expectedArray = new ArrayList<String>(Arrays.asList("leaf"));
		assertEquals(expectedArray, wordRec.getWordSuggestions("leaf", 2, 0.75, 1));
	}
	
	@Test
	void testGetWordSuggestionsNoResult() {
		int expectedLength2 = 0;
		assertEquals(expectedLength2, wordRec.getWordSuggestions("ewok", 1, 1.5, 3).size());
	}
	
	@Test
	void testGetLettersNoDuplicates() {
		ArrayList<Character> letters1 = new ArrayList<Character>(Arrays.asList('g','u','i','t','a','r'));
		assertEquals(letters1, wordRec.getLetters("guitar")); 
	}
	@Test
	void testGetLettersDublicateLetters() {
		ArrayList<Character> letters1 = new ArrayList<Character>(Arrays.asList('g','a','r','d','e','n'));
		assertEquals(letters1, wordRec.getLetters("gardener")); 
		ArrayList<Character> letters2 = new ArrayList<Character>(Arrays.asList('c','o','m','i','t','e'));
		assertEquals(letters2, wordRec.getLetters("committee")); 
		ArrayList<Character> letters3 = new ArrayList<Character>(Arrays.asList('m','i','s','p'));
		assertEquals(letters3, wordRec.getLetters("mississippi")); 
	}
	
	@Test
	void testFindCommonPercent() {
		assertEquals(5.0/6.0, wordRec.findCommonPercent("committee", "comet"));
		assertEquals(4.0/7.0, wordRec.findCommonPercent("garden", "nerdier"));
	}

}
