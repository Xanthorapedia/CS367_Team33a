import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.regex.Pattern;


public class GradeEstimator {
	// Patterns used for splitting and checking for formats
	private static final Pattern PTRN_SPLIT = Pattern.compile("\\s+|,|(#.*)+");
	private static final Pattern PTRN_WORDS = 
			Pattern.compile("\\s*[a-zA-Z]+((\\s+|,)[a-zA-Z]+)*\\s*(#.*)*");
	private static final Pattern PTRN_NUMBS = 
			Pattern.compile("\\s*\\d+(.\\d+)*((\\s+|,)\\d+(.\\d+)*)*\\s*(#.*)*");
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
				// for each file, create report
				for (String file: args) {
					try{
						est = createGradeEstimatorFromFile(file);
					}catch(FileNotFoundException e){
						System.out.println("FNF");
						//TODO gracefully exit
						return;
					}catch(GradeFileFormatException e){
						System.out.println("GFF");
						//e.printStackTrace();
						//TODO gracefully exit
						return;
					}
				}
				System.out.println(est.getEstimateReport());
			}
	}
	

	public static GradeEstimator createGradeEstimatorFromFile( String gradeInfo ) throws FileNotFoundException, GradeFileFormatException{
		Scanner stdIn = new Scanner(new File(gradeInfo));
		GradeEstimator ge = new GradeEstimator();
		ge.parse(stdIn);
		return ge;
	}


	public String getEstimateReport(){
		String estimateReport = "";
		// final weighted percent
		double weightedPercent = 0;
		
		for (int i = 0; i < scores.size(); i++) {
			Score score = scores.get(i);
			estimateReport += score.getName() + "  " + String.format("%5.2f", score.getPercent()) + "\n";
		}
		
		estimateReport += "Grades estimate is based on " + scores.size() + " scores\n";
		
		for (int i = 0; i < categoryNames.length; i++) {
			String cat = categoryNames[i];
			ScoreIterator scoreiterator = new ScoreIterator(scores, cat);
			double percent = 0;
			int count = 0;
			while(scoreiterator.hasNext()){
				Score score = scoreiterator.next();
				percent += score.getPercent();
				count++;
			}
			if (count != 0)
				percent /= count;
			
			double weight = categoryWeights[i] / 100;
			estimateReport += String.format("%7.2f", weight * percent) + "% = " + 
					String.format("%5.2f", percent) + "% of " + 
					String.format("%2.0f", categoryWeights[i]) + "% for "
					+ cat + "\n";
			weightedPercent += weight * percent;
		}
			estimateReport += "--------------------------------\n";
			
			estimateReport += String.format("%7.2f",weightedPercent) + "% weighted percent\n";
			estimateReport += "Letter Grade Estimate: ";
			
			for (int i = 0; i < miniThresholds.length; i++)
				if (weightedPercent > miniThresholds[i]) {
					estimateReport += letterGrades[i];
					break;
				}
			   
			return estimateReport;

		
	
	
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
		
		// returned ScoreList
		
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
					double earned = Double.parseDouble(splitStrings[1]);
					double max = Double.parseDouble(splitStrings[2]);
					scores.add(new Score(splitStrings[0], earned, max));
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
		
		letterGrades = grades;
		miniThresholds = thresholds;
		categoryNames = categories;
		categoryWeights = weights;
		
		stdIn.close();
	}

}

