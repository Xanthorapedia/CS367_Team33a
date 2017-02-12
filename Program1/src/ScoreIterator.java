/////////////////////////////////////////////////////////////////////////////
<<<<<<< HEAD
// Semester:         CS367 Spring 2017 
// PROJECT:          p1 Grade Estimator
// FILE:             ScoreIterator.java
//
// TEAM:    team 33
// Authors: team 33 members
// Author1: (Dasong Gao,dgao24@wisc.edu,dgao24,lec001)
// Author2: (Sam Ruh,sruh@wisc.edu,sruh,lec001)
// Author3: (Maggie Buday,Mbuday@wisc.edu,mbuday,lec001)
// Author4: (Bobby Lv,zlv7,zlv7@wisc.edu,lec001)
// Author5: (Meredith Lou,ylou9@wisc.edu,ylou9,lec001)
// Author6: (Apoorva Dhawan,dhawan3@wisc.edu,dhawan3,lec001)
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: Identify persons by name, relationship to you, and email. 
// Describe in detail the the ideas and help they provided. 
// 
// Online sources: avoid web searches to solve your problems, but if you do 
// search, be sure to include Web URLs and description of 
// of any information you find. 
//////////////////////////// 80 columns wide //////////////////////////////////
=======
// Semester:         CS367 Spring 2016 
// PROJECT:          Gradeestimator 
// FILE:             ScoreIterator.java
//
// TEAM:    Team 33
// 
//
//
>>>>>>> branch 'master' of https://github.com/TG55AA/CS367_Team33.git
import java.util.*;
<<<<<<< HEAD

public class ScoreIterator implements ScoreIteratorADT {
	private final ScoreList myScores;
	private int currPos;
	private final String category;
	private final boolean everyCat;

	public ScoreIterator(ScoreList myScores, String category) {
=======
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
>>>>>>> branch 'master' of https://github.com/TG55AA/CS367_Team33.git
		if (myScores == null || category == null)
			throw new IllegalArgumentException(); 
		//throws exception when the parameter is null
		this.myScores = myScores;
		this.currPos = 0;
		this.category = category;

		// if input == "", returns Scores from every category
		everyCat = category.equals("") ? true : false;
	}
<<<<<<< HEAD

	public boolean hasNext() {
		// while not find the assignment with the correct category, goto next
=======
	/*
	 * This method determines if there are any more items in the list
	 * (non-Javadoc)
	 * @see ScoreIteratorADT#hasNext()
	 * @return true if there are items, otherwise false
	 */
	public boolean hasNext(){
		// if not find the assignment with the correct category, goto next
>>>>>>> branch 'master' of https://github.com/TG55AA/CS367_Team33.git
		while (currPos < myScores.size() && !everyCat
<<<<<<< HEAD
				&& !myScores.get(currPos).getCategory().equals(category.substring(0, 1)))
			currPos++;
=======
				&& !myScores.get(currPos).getCategory()
				.equals(category.substring(0, 1)))
			currPos++; //increment its current position 
>>>>>>> branch 'master' of https://github.com/TG55AA/CS367_Team33.git
		return currPos < myScores.size();
	}
<<<<<<< HEAD

	public Score next() {
=======
	
	/*
	 * This method return next element from the list
	 * (non-Javadoc)
	 * @see ScoreIteratorADT#next()
	 * @ return the next element
	 */
	public Score next(){
>>>>>>> branch 'master' of https://github.com/TG55AA/CS367_Team33.git
		if (!hasNext())
			throw new NoSuchElementException();
		return myScores.get(currPos++);
	}
}
