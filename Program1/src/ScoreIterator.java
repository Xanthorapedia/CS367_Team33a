
import java.util.*;
public class ScoreIterator implements ScoreIteratorADT{
	private ScoreList myScores;
	private int currPos;
	private String category;
	private boolean everyCat;
	
	public ScoreIterator(ScoreList myScores, String category){
		if (myScores == null || category == null)
			throw new IllegalArgumentException();
		this.myScores = myScores;
		this.currPos = 0;
		this.category = category;
		
		// if input == "", returns Scores from every category
		if (category.equals(""))
			everyCat = true;
	}
	
	public boolean hasNext(){
		// while not find the assignment with the correct category, goto next
		while (currPos < myScores.size() && !everyCat
				&& !myScores.get(currPos).getCategory()
				.equals(category.substring(0, 1)))
			currPos++;
		return currPos < myScores.size();
	}
	
	public Score next(){
		if (!hasNext())
			throw new NoSuchElementException();
		return myScores.get(currPos++);
	}
}
