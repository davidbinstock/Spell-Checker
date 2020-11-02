import java.util.ArrayList;
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
		System.out.println(word1ArrayList);
		System.out.println(revword1ArrayList);
		
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
		
		double similarity = (leftSimilarity + rightSimilarity)/2;
		
		//return the figure, fix later
		//print for testing, delete later
		System.out.println(leftSimilarity);
		System.out.println(rightSimilarity);
		System.out.println(similarity);
		return similarity;
		
	}
	 public static void main(String[] args) {
		System.out.println("hello");
		WordRecommender wr = new WordRecommender("engDictionary.txt");
		wr.getSimilarity("hair", "mare");
		
	}




}

