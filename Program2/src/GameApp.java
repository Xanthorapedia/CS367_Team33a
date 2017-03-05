/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p2 Welcome to the Job Market
// FILE:             GameApp.java
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

import java.util.Scanner;
/**
 * @author Meredith Lou, Dasong Gao
 */
public class GameApp {
	/**
	 * Scanner instance for reading input from console
	 */
	private static final Scanner STDIN = new Scanner(System.in);
	/** the Game instance of the app*/
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
		args = new String[] {"20", "50"};
		int seed = -1;
		int timeToPlay = -1;
		
		// input check
		try {
			seed = Integer.parseInt(args[0]);
			if (seed < 0) {
				System.out.println("Illegal seed: nagative (" + 
						seed + ")\nGame terminated.");
				return;
			}
			
			timeToPlay = Integer.parseInt(args[1]);
			if (timeToPlay < 0) {
				System.out.println("Illegal timeToPlay: nagative (" + 
						timeToPlay + ")\nGame terminated.");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage().split(" ")[3] + 
					" is not a valid integer.\nGame terminated.");
			return;
		}
		
		GameApp gameApp = new GameApp(seed, timeToPlay);
		gameApp.start();
	}

	/**
	 * The method handles the game logic
	 */
	private void start() {
		// opending message
		System.out.println("Welcome to the Job Market!");
		
		while (!game.isOver()) {
			// round heading
			System.out.println("You have " + game.getTimeToPlay() + 
					" left in the game!");
			game.createJobs();
			game.displayActiveJobs();
			
			// choose job index from 0 to job count - 1
			int jobIndex = ragedInput("Select a job to work on: ", 0, 
					game.getNumberOfJobs() - 1);
			// time to work on greater than or equal to 0
			int jobTime = ragedInput("For how long would you like to work " +
					"on this job?: ", 0, Integer.MAX_VALUE);
			
			// update job
			Job job = game.updateJob(jobIndex, jobTime);
			if (job != null) {
				int insertTo = getIntegerInput("At what position would you like " + 
						"to insert the job back into the list?\n");
				game.addJob(insertTo, job);
			} else {
				System.out.println("Job completed! Current Score: " + 
						game.getTotalScore());
				game.displayCompletedJobs();
			}
		}
		
		// closing message
		System.out.println("Game Over!");
		System.out.println("Your final score: " + game.getTotalScore());
	}

	/**
	 * Displays the promt message and returns the integer from std input that
	 * is in the range [min, max].
	 * @param message - the prompt message displayed
	 * @param min - the minimal acceptable value of input
	 * @param max - the maximal acceptable value of input
	 * @return a integer between min and max (inclusive)
	 */
	private int ragedInput(String message, int min, int max) {
		int input;
		while (true) {
			input = getIntegerInput(message);
			if (input >= min && input <= max)
				break;
			
			// show different message according to the argument
			if (max == Integer.MAX_VALUE)
				System.out.print("Please enter an integer no smaller than " + 
						min + ".\n");
			else if (min == Integer.MIN_VALUE)
				System.out.print("Please enter an integer no greater than " + 
						max + ".\n");
			else
				System.out.print("Please enter an integer between " + min + 
						" and " + max + ".\n");
		}

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
			System.out.print(STDIN.next() + " is not an int. "
					+ "Please enter an integer.");
		}
		return STDIN.nextInt();
	}
}