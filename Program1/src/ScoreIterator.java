import java.util.*;
public class ScoreIterator implements ScoreIteratorADT{
	private ScoreList myScores;
	private int currPos;
	private String category;
	
	public ScoreIterator(ScoreList myScores, String category){
		this.myScores = myScores;
		this.currPos = 0;
		this.category = category;
	}
	
	public boolean hasNext(){
		return currPos > myScores.size();
	}
	
	public Score next(){
		if (!hasNext()) throw new NoSuchElementException();
		Score result = myScores.get(currPos);
		currPos ++;
		return result;
	}
}
