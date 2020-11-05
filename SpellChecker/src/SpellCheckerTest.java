import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpellCheckerTest {
	SpellChecker spch = new SpellChecker();
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGenerateOutputFileNameTxtSuffix() {
		String outputName1 = spch.generateOutputFileName("catch22.txt");
		assertEquals("catch22_chk.txt", outputName1);
		
		String outputName2 = spch.generateOutputFileName("theJungleBook.txt");
		assertEquals("theJungleBook_chk.txt", outputName2);
	}
	
	@Test
	void testGenerateOutputFileNameOtherSuffix() {
		String outputName1 = spch.generateOutputFileName("LifeOf.pi");
		assertEquals("LifeOf_chk.pi", outputName1);
		
		String outputName2 = spch.generateOutputFileName("CatsCradle.filesuffix");
		assertEquals("CatsCradle_chk.filesuffix", outputName2);
	}
	
	@Test
	void testIsWordInDictionaryTrue() {
		assertEquals(true, spch.isWordInDictionary("activated"));
		assertEquals(true, spch.isWordInDictionary("lackadaisical"));
		assertEquals(true, spch.isWordInDictionary("telescopic"));

	}
	
	@Test
	void testIsWordInDictionaryFalse() {
		assertEquals(false, spch.isWordInDictionary("fasdfasfancahdf"));
		assertEquals(false, spch.isWordInDictionary("ddddddddddddddd"));
		assertEquals(false, spch.isWordInDictionary("foobarfoo"));
	}
}
