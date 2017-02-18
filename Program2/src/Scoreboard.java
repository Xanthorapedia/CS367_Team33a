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
