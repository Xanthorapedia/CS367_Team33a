import java.util.Scanner;

public class GameApp{
    /**
     * Scanner instance for reading input from console
     */
    private static final Scanner STDIN = new Scanner(System.in);

    /**
     * Constructor for instantiating game class
     * @param seed: Seed value as processed in command line
     * @param timeToPlay: Total time to play from command line
     */
    public GameApp(int seed, int timeToPlay){
        //TODO: Create a new instance of Game class
    	Game game = new Game(seed,timeToPlay);
    	
    }

    /**
     * Main function which takes the command line arguments and instantiate the
     * GameApp class.
     * The main function terminates when the game ends.
     * Use the getIntegerInput function to read inputs from console
     *
     * @param args: Command line arguments <seed> <timeToPlay>
     */
    public static void main(String[] args){

        System.out.println("Welcome to the Job Market!");
      
        //TODO: Take input from command line, process it and add error checking
        int seed = Integer.parseInt(args[0]);
        int timeToPlay = Integer.parseInt(args[1]);
        GameApp gameApp = new GameApp(seed,timeToPlay);
        Game game = new Game(seed,timeToPlay);
        
        System.out.println("You have" + timeToPlay + "left in the game!");
        
        gameApp.start();
        
        /*Game Over */
         
        System.out.println("Game Over!");
        System.out.println("Your final score: " + game.getTotalScore());
        
        
        
        
        //TODO: Call the start() method to start playing the game
    }

    /**
     * Add Comments as per implementation
     */
    private void start(){
        //TODO: The interactive game logic goes here
        Game game = new Game(seed,timeToPlay);
        game.createJobs();
        game.displayActiveJobs();
        int jobIndex = readInput("Select a job to work on: ");
        int jobTime = readInput("For how long would you like to work on this job?: ");
        Job job = game.updateJob(jobIndex, jobTime);
        if(job != null){
        	int insertJob = readInput("At what position would you like to insert the job back into the list?\n ");
        	game.addJob(jobIndex,job);
        	
        }
        else{
             System.out.println("Job completed! Current Score: " + game.getTotalScore());
        }
        
    	
    }
    
    public int readInput(String message){
    	System.out.print(message);
    	int input = STDIN.nextInt();
    	if(input <= 0){
    		System.out.println("It is invalid number");
    	}
    	
    	return input;
    }

    /**
     * Displays the prompt and returns the integer entered by the user
     * to the standard input stream.
     *
     * Does not return until the user enters an integer.
     * Does not check the integer value in any way.
     * @param prompt The user prompt to display before waiting for this integer.
     */
    public static int getIntegerInput(String prompt) {
        System.out.print(prompt);
        while ( ! STDIN.hasNextInt() ) {
            System.out.print(STDIN.next()+" is not an int.  Please enter an integer.");
        }
        return STDIN.nextInt();
    }
}