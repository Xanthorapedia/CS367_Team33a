import java.util.Scanner;

public class GameApp {
	/**
	 * Scanner instance for reading input from console
	 */
	private static final Scanner STDIN = new Scanner(System.in);
	private Game game;

	/**
	 * Constructor for instantiating game class
	 * 
	 * @param seed:
	 *            Seed value as processed in command line
	 * @param timeToPlay:
	 *            Total time to play from command line
	 */
	public GameApp(int seed, int timeToPlay) {
		// TODO: Create a new instance of Game class
		game = new Game(seed, timeToPlay);

	}

	/**
	 * Main function which takes the command line arguments and instantiate the
	 * GameApp class. The main function terminates when the game ends. Use the
	 * getIntegerInput function to read inputs from console
	 *
	 * @param args:
	 *            Command line arguments <seed> <timeToPlay>
	 */
	public static void main(String[] args) {

		System.out.println("Welcome to the Job Market!");
		args = new String[] {"50", "50"};
		// TODO: Take input from command line, process it and add error checking
		int seed = Integer.parseInt(args[0]);
		int timeToPlay = Integer.parseInt(args[1]);
		GameApp gameApp = new GameApp(seed, timeToPlay);

		gameApp.start();

		/* Game Over */

		//System.out.println("Game Over!");
		// System.out.println("Your final score: " + game.getTotalScore());

		// TODO: Call the start() method to start playing the game
	}

	/**
	 * Add Comments as per implementation
	 */
	private void start() {
		while (!game.isOver()) {
			System.out.println("You have " + game.getTimeToPlay() + " left in the game!");
			game.createJobs();
			game.displayActiveJobs();
			
			int jobIndex;
			jobIndex = getIntegerInput("Select a job to work on: ");
			int jobTime = getIntegerInput("For how long would you like to work on this job?: ");
			Job job = game.updateJob(jobIndex, jobTime);
			if (job != null) {
				int insertJob = readInput("At what position would you like to insert the job back into the list?\n ");
				game.addJob(jobIndex, job);
	
			} else {
				System.out.println("Job completed! Current Score: " + game.getTotalScore());
				game.displayCompletedJobs();
			}
		}
	}

	public int readInput(String message, int min, int max) {
		int input;
		while (input < min && input > max)
		input = getIntegerInput(message);
		

		return input;
	}

	/**
	 * Displays the prompt and returns the integer entered by the user to the
	 * standard input stream.
	 *
	 * Does not return until the user enters an integer. Does not check the
	 * integer value in any way.
	 * 
	 * @param prompt
	 *            The user prompt to display before waiting for this integer.
	 */
	public static int getIntegerInput(String prompt) {
		System.out.print(prompt);
		while (!STDIN.hasNextInt()) {
			System.out.print(STDIN.next() + " is not an int.  Please enter an integer.");
		}
		return STDIN.nextInt();
	}
}