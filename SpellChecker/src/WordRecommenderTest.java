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
		fail("Not yet implemented");
	}

	@Test
	void testGetWordSuggestions() {
		fail("Not yet implemented");
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
