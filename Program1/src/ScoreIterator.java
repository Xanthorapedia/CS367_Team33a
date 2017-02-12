/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          Gradeestimator
// FILE:             ScoreIterator.java
//
// TEAM:    33 team33
// Authors: TODO who?
// Author1: Dasong Gao, dgao24@wisc.edu, dgao24, 001
// Author2: (name2,email2,netID2,lecture number2)
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: none.
// 
// Online sources: none.
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.NoSuchElementException;

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
	/** selects every category flag*/
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
			currPos++;
		return currPos < myScores.size();
	}
	
	/**
	 * This method return next element from the list
	 * @see ScoreIteratorADT#next()
	 * @ return the next element
	 */
	public Score next(){
		if (!hasNext())
			throw new NoSuchElementException();
		return myScores.get(currPos++);
	}
}
