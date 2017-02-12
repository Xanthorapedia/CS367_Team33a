/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p1 Grade Estimator
// FILE:             ScoreIterator.java
//
// TEAM:    team 33
// Authors: team 33 members
// Author1: Dasong Gao,		dgao24@wisc.edu,	dgao24,		lec001
// Author2: Sam Ruh,		sruh@wisc.edu,		sruh,		lec001
// Author3: Maggie Buday,	Mbuday@wisc.edu,	mbuday,		lec001
// Author4: Bobby Lv,		zlv7@wisc.edu,		zlv7,		lec001
// Author5: Meredith Lou,	ylou9@wisc.edu,		ylou9,		lec001
// Author6: Apoorva Dhawan,	dhawan3@wisc.edu,	dhawan3,	lec001
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: none.
// 
// Online sources: none.
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.NoSuchElementException;

/**
 *  This class is designed to iterate through a given ScoreList considering 
 *  only the items in the ScoreList that match a given category.
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
	 * Empty category ("") is considered as any known Score category in the
	 * list.
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
	 * @return the next element
	 */
	public Score next(){
		if (!hasNext())
			throw new NoSuchElementException();
		return myScores.get(currPos++);
	}
}
