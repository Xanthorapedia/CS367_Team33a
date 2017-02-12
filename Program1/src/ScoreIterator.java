/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          Gradeestimator 
// FILE:             ScoreIterator.java
//
// TEAM:    Team 33
// 
//
//
import java.util.*;
/*
 *  This class is designed to iterate through a given ScoreList considering only the items 
 *  in the ScoreList that match a given category
 */
public class ScoreIterator implements ScoreIteratorADT{
	private final ScoreList myScores; //instance variable for Scorelist class
	private int currPos;  //track iterator's current position
	private final String category; //category for different subjects
	private final boolean everyCat; 
/*
 * This method is to determine if the scorelist passed is null and 
 * throw illegal argument exception and initialize all instance variables
 * PRECONDITIONS: a list of scores and category name are passed 
 * PRECONDITIONS:return scores from every category
 */
	public ScoreIterator(ScoreList myScores, String category){
		if (myScores == null || category == null)
			throw new IllegalArgumentException(); 
		//throws exception when the parameter is null
		this.myScores = myScores;
		this.currPos = 0;
		this.category = category;
		
		// if input == "", returns Scores from every category
		everyCat = category.equals("") ? true : false;
	}
	/*
	 * This method determines if there are any more items in the list
	 * (non-Javadoc)
	 * @see ScoreIteratorADT#hasNext()
	 * @return true if there are items, otherwise false
	 */
	public boolean hasNext(){
		// if not find the assignment with the correct category, goto next
		while (currPos < myScores.size() && !everyCat
				&& !myScores.get(currPos).getCategory()
				.equals(category.substring(0, 1)))
			currPos++; //increment its current position 
		return currPos < myScores.size();
	}
	
	/*
	 * This method return next element from the list
	 * (non-Javadoc)
	 * @see ScoreIteratorADT#next()
	 * @ return the next element
	 */
	public Score next(){
		if (!hasNext())
			throw new NoSuchElementException();
		return myScores.get(currPos++);
	}
}
