import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;


public class GradeEstimator {
	// Patterns used for splitting and checking for formats
	private static final Pattern PTRN_SPLIT = Pattern.compile("\\s+|(#.*)+");
	private static final Pattern PTRN_WORDS = 
			Pattern.compile("\\s*[a-zA-Z]+(\\s+[a-zA-Z]+)*\\s*(#.*)*");
	private static final Pattern PTRN_NUMBS = 
			Pattern.compile("\\s*\\d+(.\\d+)*(\\s+\\d+(.\\d+)*)*\\s*(#.*)*");
	private static final Pattern PTRN_ASGNS = 
			Pattern.compile("\\s*[a-zA-Z]\\d*(\\s+\\d+(.\\d+)*){2}\\s*(#.*)*");
	
	private String [] letterGrades;
	private double [] miniThresholds; 
	private String [] categoryNames;
	private double [] categoryWeights;
	
	private ScoreList scores = new ScoreList();
	
	public static void main(String[] args) {
		GradeEstimator est = new GradeEstimator();
		// if no args, create default report
		if (args.length != 1) {
			System.out.println(Config.USAGE_MESSAGE);
			try {
				est.parse(new Scanner(Config.GRADE_INFO_FILE_FORMAT_EXAMPLE));
				System.out.println(est.getEstimateReport());
			} catch (GradeFileFormatException e) {
				// Not gonna happen
				e.printStackTrace();
			}
		}
		else {
			// create and print report
			try{
				est = createGradeEstimatorFromFile(args[0]);
			} catch(FileNotFoundException e) {
				System.out.println("Sorry, the file \""+ args[0]
						+ "\" is not found.");
				return;
			} catch(GradeFileFormatException e) {
				System.out.println("Sorry, the file \""+ args[0]
						+ "\" is corrupted:\n" + e.getMessage());
				return;
			}
			System.out.println(est.getEstimateReport());
		}
			
	}
	

	public static GradeEstimator createGradeEstimatorFromFile( String gradeInfo ) 
			throws FileNotFoundException, GradeFileFormatException{
		Scanner stdIn = new Scanner(new File(gradeInfo));
		GradeEstimator ge = new GradeEstimator();
		ge.parse(stdIn);
		return ge;
	}


	public String getEstimateReport(){
		String estimateReport = "";
		// final weighted percent
		double weightedPercentTotal = 0;
		ScoreIterator iter = new ScoreIterator(scores, "");
		
		// print all assignments regardless of the category
		while(iter.hasNext()) {
			Score score = iter.next();
			estimateReport += score.getName() + 
					String.format("%7.2f", score.getPercent()) + "\n";
		}
		
		estimateReport += "\nGrades estimate is based on " + scores.size() + 
				" scores\n";
		
		// calculate the average percent of each category
		for (int i = 0; i < categoryNames.length; i++) {
			String cat = categoryNames[i];
			iter = new ScoreIterator(scores, cat);
			
			// add up all percentages
			double percent = 0;
			int count = 0;
			while(iter.hasNext()) {
				Score score = iter.next();
				percent += score.getPercent();
				count++;
			}
			// if no score for a category, average percent = 100
			percent = (count == 0 ? 100 : percent / count);
			
			double weightPercent = categoryWeights[i] / 100 * percent;
			estimateReport += String.format("%7.2f", weightPercent) + "% = " + 
					String.format("%5.2f", percent) + "% of " + 
					String.format("%2.0f", categoryWeights[i]) + "% for " + 
					cat + "\n";
			weightedPercentTotal += weightPercent;
		}
		
		estimateReport += "--------------------------------\n";
		estimateReport += String.format("%7.2f", weightedPercentTotal) + 
				"% weighted percent\n";
		estimateReport += "Letter Grade Estimate: ";
		
		for (int i = 0; i < miniThresholds.length; i++)
			if (weightedPercentTotal >= miniThresholds[i])
				return estimateReport += letterGrades[i];
		
		return estimateReport += 
			"Letter Grade Estimate: unable to estimate letter grade for " +
			weightedPercentTotal;
		   
	}
	
	/**
	 * A method to check and parse the file. Any error is indicated in exception message 
	 * The method checks for:
	 * 	1. if each of the line is legal
	 * 	2. if grades correspond to thresholds
	 * 	3. if thresholds are in decreasing order
	 * 	4. if weights add up 100
	 * 	5. if weights correspond to categories
	 * 	6. if the category does not exist for an assignment
	 * 
	 * @param path - file path
	 * @throws FileNotFoundException - if file is not found
	 * @throws Exception - basically GradeFileFormatException, in case you don't have that
	 */
	private void parse(Scanner stdIn) throws GradeFileFormatException {
		// file input and current line
		String tmp = null;
		
		// all grade letters, corresponding thresholds, 
		// category of assignments and corresponding weights
		String[] grades = null;
		double[] thresholds = null;
		String[] categories = null;
		double[] weights = null;
		
		// regex matching pattern
		Pattern pattern = null;
		
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
						thresholds[j] = Double.parseDouble(splitStrings[j]);
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
					// check if the category exists
					boolean found = false;
					for (String cat : categories) {
						if (splitStrings[0].charAt(0) == cat.charAt(0))
							found = true;
					}
					if (!found)
						throw new GradeFileFormatException(
								"Unknown Assignment Category: " + splitStrings[0]);
					
					// parse and add assignment
					double earned = Double.parseDouble(splitStrings[1]);
					double max = Double.parseDouble(splitStrings[2]);
					scores.add(new Score(splitStrings[0], earned, max));
				}
			}
			else {
				stdIn.close();
				throw new GradeFileFormatException("Bad Line (" + i + "): " + tmp);
			}
			
			i++;
		}
		stdIn.close();
		
		if (i < 4)
			throw new GradeFileFormatException("Unexpected File Header End: " + tmp);
		
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
		
		letterGrades = grades;
		miniThresholds = thresholds;
		categoryNames = categories;
		categoryWeights = weights;
		
		stdIn.close();
	}
}
