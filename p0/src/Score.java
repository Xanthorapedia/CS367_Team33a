/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p0
// FILE:             ScoreList.java
// 	                 Score.java 
// 	                 ScoreListADT.java
// 	                 Test_ScoreList.java
//
// Author1: Dasong Gao, dgao24@wisc.edu, 9075308438, 001
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: None
// 
// Online sources: None
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * The instance of this class stores the earned points of an assignment, along 
 * with the assignment name and maximal possible points.
 * 
 * <p>Bugs: none known
 * 
 * @author Dasong Gao
 *
 */
public class Score {
	
	/** The assignment name.*/
	private String name;
	
	/** The earned points of the assignment.*/
	private double earnedPoints;
	
	/** The maximal points of the assignment.*/
	private double possiblePoints;

	/**
	 * Constructs a Score object with specific name, earned points and maximal
	 * possible points. Will throw IllegalArgumentException if name is null,
	 * earned points or maximal points is negative, or earned points > maximal
	 * points.
	 * 
	 * @param name - the assignment name
	 * @param points - the points earned
	 * @param possible - the maximal possible points
	 * @throws IllegalArgumentException
	 */
	public Score(String name, double points, double possible) throws IllegalArgumentException {
		// argument check
		if (name == null || points > possible || points < 0 || possible < 0)
			throw new IllegalArgumentException();
		
		this.name = name;
		this.earnedPoints = points;
		this.possiblePoints = possible;
	}

	/**
	 * This method returns the name of the assignment.
	 * @return the name of the assignment.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method returns the earned points of the assignment.
	 * @return the earned points of the assignment.
	 */
	public double getPoints() {
		return earnedPoints;
	}
	
	/**
	 * This method returns the maximal possible points of the assignment.
	 * @return the maximal possible points of the assignment.
	 */
	public double getMaxPossible() {
		return possiblePoints;
	}

	/**
	 * This method returns the category of the assignment, i.e. the first
	 * character of the assignment name.
	 * @return the category of the assignment.
	 */
	public String getCategory() {
		return name.substring(0, 1);
	}
	
	/**
	 * This method returns the percentage of earned points out of maximum
	 * possible points.
	 * @return the percentage of the assignment.
	 */
	public double getPercent() {
		return earnedPoints / possiblePoints * 100;
	}
	
}
