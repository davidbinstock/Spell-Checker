import java.util.ArrayList;

public class WordRecommender {
	
	public WordRecommender(String fileName) {}
	public double getSimilarity(String word1, String word2) {
	        
	        ArrayList<Character> word1ArrayList=new ArrayList<Character>();
	        ArrayList<Character> revword1ArrayList=new ArrayList<Character>();
	        for(int i=0;i<word1.length();i++)
	        {
	        	word1ArrayList.add(word1.charAt(i));
	        	revword1ArrayList.add(word1.charAt(word1.length() - i -1));
	        }
	        System.out.println(word1ArrayList);
	        System.out.println(revword1ArrayList);
	        return 2.4;
	    }
		
	

		
		
	}

