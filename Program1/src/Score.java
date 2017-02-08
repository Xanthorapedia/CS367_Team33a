/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p0
// FILE:             Score
//
// Author: Sam Ruh
// Author1: Sam Ruh, sruh@wisc.edu, ruh, lec 001
//
////////////////////////////////////////////////////////////////////////////


/**
 * This class is an instantiable class for holding test results. It has three
 * fields it will be storing for each instance of this class that is created.
 *
 *
 * @author Sam Ruh	
 */
public class Score {
	private String name; //the name of the student 
	private double pointsEarn; //the number of points the student earned
	private double pointsPos; //the total possible points for the test
	
	/**
	 * This is the constructor for the Score class. It will check to make 
	 * sure none of the variables sent to it are legal. 
	 *
	 * PRECONDITIONS: none
	 * 
	 * POSTCONDITIONS: the three class variables are set to the values sent in
	 * the instantiation of the class
	 *
	 * @param name a string to represent the name of the student whose scores
	 * are being saved
	 * @param pointsEarn the number of points the student earned
	 * @param pointsPos the number of points possible on the given assignment
	 *
	 */
	public Score(String name, double pointsEarn, double pointsPos ){
		if (name == null || pointsEarn < 0 || pointsPos < 0 
				|| pointsEarn > pointsPos)
			throw new IllegalArgumentException();
		this.name = name;
		this.pointsEarn = pointsEarn;
		this.pointsPos = pointsPos;
	}
	
	/**
	 * Simply returns the name 
	 *
	 * PRECONDITIONS: name has a value
	 * 
	 * POSTCONDITIONS: name is returned 
	 *
	 * @return the name of the students whose grades are in this object
	 */
	String getName(){
		return name;
	}
	
	/**
	 * Simply returns the points earned by the student
	 *
	 * PRECONDITIONS: getPoints has a value
	 * 
	 * POSTCONDITIONS: getPoints is returned 
	 *
	 * @return the number of points the student earned is returned
	 */
	double getPoints(){
		return pointsEarn;
	}
	
	/**
	 * Simply returns the maximum number of points possible
	 *
	 * PRECONDITIONS: pointsPos has a value
	 * 
	 * POSTCONDITIONS: pointsPos is returned 
	 *
	 * @return the number of possible points is returned
	 */
	double getMaxPossible(){
		return pointsPos;
	}
	
	/**
	 * Simply returns the first letter of name 
	 *
	 * PRECONDITIONS: name has a value
	 * 
	 * POSTCONDITIONS: the first letter of name is returned 
	 *
	 * @return the first letter of name
	 */
	String getCategory(){
		return name.substring(0, 1);
	}
	
	/**
	 * Simply returns percentage the student got correct
	 *
	 * PRECONDITIONS: pointsEarn and pointsPos a both have values
	 * 
	 * POSTCONDITIONS: the percentage correct is returned
	 *
	 * @return the students final percentage on the given assignment
	 */
	double getPercent(){
		return (pointsEarn / pointsPos) * 100;
	}
}
