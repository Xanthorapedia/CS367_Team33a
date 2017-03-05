/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p2 Welcome to the Job Market
// FILE:             Scoreboard.java
//
// TEAM:    team 33
// Authors: team 33 members
// Author0: Apoorva Dhawan,	dhawan3@wisc.edu,	dhawan3,	lec001
// Author1: Bobby Lv,		zlv7@wisc.edu,		zlv7,		lec001
// Author2: Dasong Gao,		dgao24@wisc.edu,	dgao24,		lec001
// Author3: Maggie Buday,	Mbuday@wisc.edu,	mbuday,		lec001
// Author4: Meredith Lou,	ylou9@wisc.edu,		ylou9,		lec001
// Author5: Sam Ruh,		sruh@wisc.edu,		sruh,		lec001
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: none.
// 
// Online sources: none.
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class implements the scoreboard for the game, which is responsible
 * for maintaining the completed Jobs, calculating the total score and display
 * the board textually.
 * 
 * @author Dasong Gao
 *
 */
public class Scoreboard implements ScoreboardADT {

	/** the list of jobs completed*/
	private ListADT<Job> list;

	/** total score earned*/
	private int totalScore;
	
	/**
	 * Constructs a scoreboard object.
	 */
	public Scoreboard(){
	    totalScore = 0; 
	    list = new JobList();
	}

	/**
	 * This method calculates the total score earned.
	 * @return total score earned.
	 */
	@Override
	public int getTotalScore() {
		return totalScore;
	}

	/**
	 * The method adds a new completed Job to the board and updates total score.
	 * @param job - the Job to be added.
	 */
	@Override
	public void updateScoreBoard(Job job) {
		if (job == null)
			throw new IllegalArgumentException();

		list.add(job);
		totalScore += job.getPoints();
	}

	/**
	 * This method displays the scoreboard of the game in the following format:
	 * <i><p>Total Score: &lt;score&gt;
	 * <br>The jobs completed: 
	 * <br>Job Name: &lt;jobName&gt;
	 * <br>Points earned for this job: &lt;points&gt;
	 * <br>--------------------------------------------
	 */
	@Override
	public void displayScoreBoard() {
		System.out.println("Total Score: " + totalScore);
		System.out.println("The jobs completed: ");
		for (Job job : list)
			System.out.println(
					"Job Name: " + job.getJobName() + "\n" +
					"Points earned for this job: " + job.getPoints() + "\n" +
					"--------------------------------------------");
	}

}
