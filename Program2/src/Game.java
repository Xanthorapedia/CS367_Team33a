/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p2 Welcome to the Job Market
// FILE:             Game.java
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
 * This class is used to keep track of the score board. 
 * This includes all of the calls to the score board method 
 * to keep track of score and jobs on the score board as well as 
 * the amount of timeToPLay left. 
 *
 * @author Meredith Lou
 */

public class Game {

	/** A list of all jobs currently in the queue. */
	private ListADT<Job> list;
	/** the scoreboard containing completed jobs */
	private ScoreboardADT scoreBoard;
	/** the remaining time to play in the game */
	private int timeToPlay;
	private JobSimulator jobSimulator;

	/**
	 * Constructor. Initializes all variables.
	 * 
	 * @param seed
	 *            seed used to seed the random number generator in the
	 *            Jobsimulator class.
	 * @param timeToPlay
	 *            duration used to determine the length of the game.
	 */
	public Game(int seed, int timeToPlay) {
		this.scoreBoard = new Scoreboard();
		this.timeToPlay = timeToPlay;
		this.jobSimulator = new JobSimulator(seed);
		this.list = new JobList();
	}

	/**
	 * Returns the amount of time currently left in the game.
	 * 
	 * @returns the amount of time left in the game.
	 */
	public int getTimeToPlay() {
		return timeToPlay;
	}

	/**
	 * Sets the amount of time that the game is to be executed for. Can be used
	 * to update the amount of time remaining.
	 * 
	 * @param timeToPlay
	 *            the remaining duration of the game
	 */
	public void setTimeToPlay(int timeToPlay) {
		this.timeToPlay = timeToPlay;
	}

	/**
	 * States whether or not the game is over yet.
	 * 
	 * @returns true if the amount of time remaining in the game is less than or
	 *          equal to 0, else returns false
	 */
	public boolean isOver() {
		return timeToPlay <= 0;
	}

	/**
	 * This method simply invokes the simulateJobs method in the JobSimulator
	 * object.
	 */
	public void createJobs() {
		jobSimulator.simulateJobs(list, timeToPlay);

	}

	/**
	 * @returns the length of the Joblist.
	 */
	public int getNumberOfJobs() {
		return list.size();
	}

	/**
	 * Adds a job to a given position in the joblist. Also requires to calculate
	 * the time Penalty involved in adding a job back into the list and update
	 * the timeToPlay accordingly
	 * 
	 * @param pos
	 *            The position that the given job is to be added to in the list.
	 * @param item
	 *            The job to be inserted in the list.
	 */
	public void addJob(int pos, Job item) {
		list.add(pos, item);
		
		// time penalty
		if (pos < 0)
			timeToPlay -= list.size();
		else
			timeToPlay -= pos;
	}

	/**
	 * Adds a job to the joblist.
	 * 
	 * @param item
	 *            The job to be inserted in the list.
	 */
	public void addJob(Job item) {
		list.add(item);
	}

	/**
	 * Given a valid index and duration, executes the given job for the given
	 * duration.
	 *
	 * This function should remove the job from the list and return it after
	 * applying the duration.
	 *
	 * This function should set duration equal to the amount of time remaining
	 * if duration exceeds it prior to executing the job. After executing the
	 * job for a given amount of time, check if it is completed or not. If it
	 * is, then it must be inserted into the scoreBoard. This method should also
	 * calculate the time penalty involved in executing the job and update the
	 * timeToPlay value accordingly
	 * 
	 * @param index
	 *            The job to be inserted in the list.
	 * @param duration
	 *            The amount of time the given job is to be worked on for.
	 */
	public Job updateJob(int index, int duration) {
		// penalty for not choosing 0
		timeToPlay -= index;
		Job job = list.remove(index);

		// steps remaining to complete a Job
		int stepsRemaining = job.getTimeUnits() - job.getSteps();
		// in case there is actually no enough time to complete the Job
		duration = Integer.min(timeToPlay, duration);
		// the actual time spent on the Job
		int timeWorked = Integer.min(stepsRemaining, duration);
		job.setSteps(job.getSteps() + timeWorked);

		timeToPlay -= timeWorked;

		// update the score board if the job is completed
		if (job.isCompleted()) {
			scoreBoard.updateScoreBoard(job);
			return null;
		} else
			return job;
	}

	/**
	 * This method produces the output for the initial Job Listing, IE: "Job
	 * Listing At position: job.toString() At position: job.toString() ..."
	 *
	 */
	public void displayActiveJobs() {

		System.out.println("Job Listing");
		int i = 0;
		for (Job job : list) {
			System.out.println("At position: " + i + " " + job);
			i++;
		}
		System.out.println("");
	}

	/**
	 * This function simply invokes the displayScoreBoard method in the
	 * ScoreBoard class.
	 */
	public void displayCompletedJobs() {
		scoreBoard.displayScoreBoard();
	}

	/**
	 * This function simply invokes the getTotalScore method of the ScoreBoard
	 * class.
	 * 
	 * @return the value calculated by getTotalScore
	 */
	public int getTotalScore() {
		return scoreBoard.getTotalScore();
	}

}
