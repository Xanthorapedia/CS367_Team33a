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

/**
 * 
 * @author TODO
 *
 */
public interface ScoreIteratorADT{
	/**
	 * This method determines if there are any more items in the list
	 * @return true if there are items, otherwise false
	 */
	boolean hasNext();
	
	/**
	 * This method return next element from the list
	 * @return the next element
	 */
	Score next();
}
