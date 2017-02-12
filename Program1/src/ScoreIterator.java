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

/**
 *  This class is designed to iterate through a given ScoreList considering 
 *  only the items in the ScoreList that match a given category
 *  @auther TODO who?
 */
public class ScoreIterator implements ScoreIteratorADT{
	/** instance variable for Scorelist class*/
	private final ScoreList myScores;
	/** current position*/
	private int currPos;
	/** category of this iterator*/
	private final String category;
	private final boolean everyCat; 
	
	/**
	 * The consructor sets up a iterator of a ScoreList of a given category.
	 * Throws IllegalArgumentException if either of the arguments is null.
	 * @param myScores - the source of the iterator
	 * @param category - the category of the iterator
	 */
	public ScoreIterator(ScoreList myScores, String category){
		// throws exception when the parameter is null
		if (myScores == null || category == null)
			throw new IllegalArgumentException(); 
		
		this.myScores = myScores;
		this.currPos = 0;
		this.category = category;
		
		// if input == "", returns Scores in every category
		everyCat = category.equals("") ? true : false;
	}
	/**
	 * This method determines if there are any more items in the list
	 * @see ScoreIteratorADT#hasNext()
	 * @return true if there are items, otherwise false
	 */
	public boolean hasNext(){
		// if the assignment with the correct category is not found, goto next
		while (currPos < myScores.size() && !everyCat
				&& !myScores.get(currPos).getCategory()
				.equals(category.substring(0, 1)))
			currPos++; //increment its current position 
		return currPos < myScores.size();
	}
	
	/**
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
