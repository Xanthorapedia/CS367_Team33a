import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.regex.Pattern;


public class GradeEstimator {
	// Patterns used for splitting and checking for formats
	private static final Pattern PTRN_SPLIT = Pattern.compile("\\s+|(#.*)+");
	private static final Pattern PTRN_WORDS = 
			Pattern.compile("\\s*[a-zA-Z]+(\\s+[a-zA-Z]+)*\\s*(#.*)*");
	private static final Pattern PTRN_NUMBS = 
			Pattern.compile("\\s*\\d+(\\s+\\d+)*\\s*(#.*)*");
	private static final Pattern PTRN_ASGNS = 
			Pattern.compile("\\s*[a-zA-Z]\\d*(\\s+\\d+){2}\\s*(#.*)*");
	
	private String [] letterGrades;
	private double [] miniThresholds; 
	private String [] categoryNames;
	private double [] categoryWeights;
	
	private ScoreList scores;
	
	public static void main(String[] args) {
		if (args.length == 0){
			System.out.println(Config.USAGE_MESSAGE);
			letterGrades = Config.GRADE_LETTER;
			miniThresholds = Config.GRADE_THRESHOLD;
			categoryNames = Config.CATEGORY_KEY;
			categoryWeights = Config.CATEGORY_WEIGHT;
			
		}else{
			for (String file: args)
				try{
			
					GradeEstimator thisGradeestimater = createGradeEstimatorFromFile(file);
					System.out.println(getEstimateReport());
			
				}catch(FileNotFoundException e){

				}catch(GradeFileFormatException e){
			
				}
			}
	}
	
	public GradeEstimator(String[] letterGrades, double[] miniThresholds, String[] categoryNames,
			double[] categoryWeights, ScoreList scores) {
		super();
		this.letterGrades = letterGrades;
		this.miniThresholds = miniThresholds;
		this.categoryNames = categoryNames;
		this.categoryWeights = categoryWeights;
		this.scores = scores;
	}




	public static GradeEstimator createGradeEstimatorFromFile( String gradeInfo ) throws FileNotFoundException, GradeFileFormatException{


		try{
			parseFile(gradeInfo);


		}catch(FileNotFoundException e){
			throw new FileNotFoundException();

		}
		catch(GradeFileFormatException e){
			

		}
		finally{
			return null;
		}
	}


	public static String getEstimateReport(){
	    
		return "";
	
	
	}
	
	/**
	 * A method to check and parse the file. Any error is indicated in exception message 
	 * The method checks for:
	 * 	1. if each of the line is legal
	 * 	2. if grades correspond to thresholds
	 * 	3. if thresholds are in decreasing order
	 * 	4. if weights add up 100
	 * 	5. if weights correspond to categories
	 * can be added:
	 * 	1. if has same assignment name
	 * 	2. if no such category for an assignment
	 * 
	 * @param path - file path
	 * @throws FileNotFoundException - if file is not found
	 * @throws Exception - basically GradeFileFormatException, in case you don't have that
	 */
	public ScoreList parseFile(String path) 
			throws FileNotFoundException, GradeFileFormatException {
		// file input and current line
		Scanner stdIn = new Scanner(new File(path));
		String tmp = null;
		
		// all grade letters, corresponding thresholds, 
		// category of assignments and corresponding weights
		String[] grades = letterGrades;
		double[] thresholds = miniThresholds;
		String[] categories = categoryNames;
		double[] weights = categoryWeights;
		
		// regex matching pattern
		Pattern pattern = null;
		
		// returned ScoreList
		ScoreList list = new ScoreList();
		
		// Line count
		int i = 0;
		while (stdIn.hasNextLine()) {
			tmp = stdIn.nextLine();
			// parse words: word0 ( word1 word2...) (#comment)
			if (i == 0 || i == 2)
				pattern = PTRN_WORDS;
			// parse numbers: num0 ( num1 num2...) (#comment)
			else if (i == 1 || i == 3)
				pattern = PTRN_NUMBS;
			// parse assignments: category(num) pEarned pPossible (#comment)
			else
				pattern = PTRN_ASGNS;
			
			// if matches, split to get info; if no match, bad file
			if (pattern.matcher(tmp).matches()) {
				String[] splitStrings = PTRN_SPLIT.split(tmp);
				// grades line
				if (i == 0)
					grades = splitStrings;
				// thresholds line, parse thresholds and put into int[]
				else if (i == 1) {
					thresholds = new double[splitStrings.length];
					for (int j = 0; j < splitStrings.length; j++)
						thresholds[j] = Integer.parseInt(splitStrings[j]);
				}
				// categories line
				else if (i == 2)
					categories = splitStrings;
				// weights line, parse thresholds and put into int[]
				else if (i == 3) {
					weights = new double[splitStrings.length];
					for (int j = 0; j < splitStrings.length; j++)
						weights[j] = Integer.parseInt(splitStrings[j]);
				}
				// any other assignment lines
				else {
					double earned = Double.parseDouble(splitStrings[1]);
					double max = Double.parseDouble(splitStrings[2]);
					list.add(new Score(splitStrings[0], earned, max));
				}
			}
			else {
				stdIn.close();
				throw new GradeFileFormatException("Bad line (" + i + "): " + tmp);
			}
			
			i++;
		}
		stdIn.close();
		
		// #grades should = #thresholds
		if (grades.length != thresholds.length) {
			throw new GradeFileFormatException("Grades Thresholds Mismatch: " 
					+ grades.length + ":" + thresholds.length);
		}
		
		// thresholds should be in decreasing order
		for (int j = 0; j < thresholds.length - 1; j++)
			if (thresholds[j] < thresholds[j + 1])
				throw new GradeFileFormatException("Thresholds out of Order: " 
						+ thresholds[j] + " " + thresholds[j + 1]);
		
		// #weights should = #categories
		if (categories.length != weights.length)
			throw new GradeFileFormatException("Categories Weights Mismatch: " 
					+ categories.length + ":" + weights.length);
		
		// weights should add up 100
		int sum = 0;
		for (int j = 0; j < weights.length; j++)
			sum += weights[j];
		if (sum != 100)
			throw new GradeFileFormatException("Bad Weights: sum = " + sum);
		
		
		stdIn.close();
		return list;
	}

}

