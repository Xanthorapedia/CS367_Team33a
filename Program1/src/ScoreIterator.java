/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
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
import java.util.*;

public class ScoreIterator implements ScoreIteratorADT {
	private final ScoreList myScores;
	private int currPos;
	private final String category;
	private final boolean everyCat;

	public ScoreIterator(ScoreList myScores, String category) {
		if (myScores == null || category == null)
			throw new IllegalArgumentException();
		this.myScores = myScores;
		this.currPos = 0;
		this.category = category;

		// if input == "", returns Scores from every category
		everyCat = category.equals("") ? true : false;
	}

	public boolean hasNext() {
		// while not find the assignment with the correct category, goto next
		while (currPos < myScores.size() && !everyCat
				&& !myScores.get(currPos).getCategory().equals(category.substring(0, 1)))
			currPos++;
		return currPos < myScores.size();
	}

	public Score next() {
		if (!hasNext())
			throw new NoSuchElementException();
		return myScores.get(currPos++);
	}
}
