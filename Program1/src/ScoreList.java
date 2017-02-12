/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p1 Grade Estimator
// FILE:             ScoreList.java
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


/**
 * This class will store and keep track of a list of scores. It can get,
 * remove, and add scores. It is an instantiable class so more than one 
 * list can be made. 
 */
public class ScoreList implements ScoreListADT {
	//an array to store all of the score objects
	private Score[] list = new Score[1];
	
	//the number of scores in the array
	private int numScores = 0;
	
	/**
	 * Simply returns number of items in the list
	 *
	 * PRECONDITIONS: none
	 * 
	 * POSTCONDITIONS: numScore is returned
	 *
	 * @return the number of items in the list
	 */
	public int size() {
		return numScores;
	}
	
	/**
	 * Adds a new score object to the array of scores. It also checks to
	 * make sure the score object it is sent is not null, and therefore will
	 * have no preconditions. 
	 *
	 * PRECONDITIONS: none
	 * 
	 * POSTCONDITIONS: a new score object is added to the list of score object
	 *
	 * @param s a new score object to be added to the list of score objects
	 */
	public void add(Score s) throws IllegalArgumentException {
		/*checks to make sure the given score object is not null so the
		 *  ScoreList class follows ADT rules and is contiguous*/
		if (s == null)
			throw new IllegalArgumentException();
		
		if (numScores == list.length)
			expandArray();
		list[numScores] = s;
		numScores++;
	}
	
	private void expandArray() {
		Score[] a = new Score[list.length * 2];
		for (int i = 0; i < numScores; i++)
			a[i] = list[i];
		list = a;
	}
		
	
	/**
	 * This method removes and returns the score object at the given location.
	 *
	 * PRECONDITIONS: the list must have some items in it to remove
	 * 
	 * POSTCONDITIONS: a score is removed from the list and returned
	 *
	 * @param i is an index for a given score object in the list 
	 * @return the score object at the given index
	 */
	public Score remove(int i) throws IndexOutOfBoundsException {
		//checks to make sure the given index is in bounds
		if (i < 0 || i > numScores)
			throw new IndexOutOfBoundsException();

		Score score = list[i];
		
		//shifts all the objects to the right of the requested object to the
		//left one space to effectively 'delete' the requested object
		for (int index = i; index < numScores - 1; index++) {
	        list[index] = list[index + 1];
	    }
		
		numScores --;
		return score;	
	}
	
	/**
	 * This method simply returns the score object at the given index
	 * 
	 * PRECONDITIONS: the list must have some items in it to get
	 * 
	 * POSTCONDITIONS: a score is returned
	 *
	 * @param i is an index for a given score object in the list 
	 * @return the score object at the given index
	 */
	public Score get(int i) throws IndexOutOfBoundsException {
		//checks to make sure the given index is in bounds
		if (i < 0 || i > numScores)
			throw new IndexOutOfBoundsException();
		
		return list[i];
	}
}
