/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p1 Grade Estimator
// FILE:             GradeEstimator.java
//
// TEAM:    team 33
// Authors: team 33 members
// Author1: Dasong Gao,		dgao24@wisc.edu,	dgao24,		lec001
// Author2: Sam Ruh,		sruh@wisc.edu,		sruh,		lec001
// Author3: Maggie Buday,	Mbuday@wisc.edu,	mbuday,		lec001
// Author4: Bobby Lv,		zlv7@wisc.edu,		zlv7,		lec001
// Author5: Meredith Lou,	ylou9@wisc.edu,		ylou9,		lec001
// Author6: Apoorva Dhawan,	dhawan3@wisc.edu,	dhawan3,	lec001
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: none.
// 
// Online sources: 
//		Pattern Class usage:
//		https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class is a tool for students in class to esimate the grade they may
 * receive in a class. It takes a single input file about grade information from
 * a single student and then uses it to generate a grade report for that
 * student. The grade reports shows the percent and weighted percent grades that
 * student receives in each assignment category. And it also generate an
 * estimated letter grade for this student.
 *
 * <p>Bugs: (a list of bugs and other problems)
 *
 * @author Team 33 Member
 */

public class GradeEstimator {
	/** Pattern for spliting line: split at whitespace or #comment*/
	private static final Pattern PTRN_SPLIT = Pattern.compile("\\s+|(#.*)+");
	/** Pattern for words line: word0 ( word1 word2...) (#comment)*/
	private static final Pattern PTRN_WORDS = 
			Pattern.compile("\\s*[a-zA-Z]+(\\s+[a-zA-Z]+)*\\s*(#.*)*");
	/** Pattern for numbers line: num0 ( num1 num2...) (#comment)*/
	private static final Pattern PTRN_NUMBS = 
			Pattern.compile("\\s*\\d+(.\\d+)*(\\s+\\d+(.\\d+)*)*\\s*(#.*)*");
	/** Pattern for assignments line: letter(num) num1 num2 (#comment)*/
	private static final Pattern PTRN_ASGNS = 
			Pattern.compile("\\s*[a-zA-Z]\\d*(\\s+\\d+(.\\d+)*){2}\\s*(#.*)*");
	
	/** Grade letters*/
	private String [] letterGrades;
	/** Grade thresholds*/
	private double [] miniThresholds;
	/** Category names*/
	private String [] categoryNames;
	/** Category weights*/
	private double [] categoryWeights;
	
	private ScoreList scores = new ScoreList();
	
	/**
	 * This is the main method for the GradeEstimator class. If a file is not
	 * passed in through args[], it will use default information in Config.java
	 * 
	 * This method also handles the exception that may be thrown by other method
	 * and create an instance of GradeEstimator. 
	 * 
	 * @param args
	 *            takes a single input file containing grade information
	 */
	public static void main(String[] args) {
		GradeEstimator est = new GradeEstimator();
		// if no args, create default report
		if (args.length != 1) {
			//if there are not only one file, print out default message
			System.out.println(Config.USAGE_MESSAGE); 
			try {
				//parse and scan the file
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
				//arg[0] is the file name
				est = createGradeEstimatorFromFile(args[0]);
			} catch(FileNotFoundException e) {
				System.out.println("java.io.FileNotFoundException: "+ args[0]
						+ " (No such file or directory)");
				return;
			} catch(GradeFileFormatException e) {
				System.out.println("java.io.GradeFileFormatException: "+ args[0]
						+ " (No such file or directory)" + e.getMessage());
				return;
			}
			System.out.println(est.getEstimateReport());
		}
			
	}
	
	/**
	 * This method creates a new instance of the GradeEstimator class using the
	 * filename gradeInfo. It reads the gradeInfo and create an instance of 
	 * GradeEstimator using the information from the gradeInfo file.
	 * 
	 * @param gradeInfo
	 *            is a String containing grade informations
	 * @return an instance of GradeEstimator
	 * @throws FileNotFoundException
	 *             if given filename does not exist or if the format of the file
	 *             is wrong
	 * @throws GradeFileFormatException
	 *             if a line is not in the expected format
	 */
	public static GradeEstimator createGradeEstimatorFromFile(String gradeInfo)
			throws FileNotFoundException, GradeFileFormatException{
		Scanner stdIn = new Scanner(new File(gradeInfo));
		
		GradeEstimator ge = new GradeEstimator();
		ge.parse(stdIn);
		return ge;
	}

	/**
	 * This method returns a string that contains the calculated weighted score
	 * from each category, the percent grades they received on each assignment.
	 * The overall score will be also calculated and this method assigns a
	 * letter grade to this student.
	 *
	 * @return a String representing a grade report for the student
	 */
	public String getEstimateReport(){
		// report results
		String estimateReport = "";
		// final weighted percent
		double weightedPercentTotal = 0;
		// Score iterator
		ScoreIterator iter = null;
		
		// print all assignments regardless of the category
		iter = new ScoreIterator(scores, "");
		while(iter.hasNext()) {
			Score score = iter.next();
			estimateReport += String.format("%s%7.2f\n", 
					score.getName(), score.getPercent());
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
			percent = count == 0 ? 100 : percent / count;
			
			double weightPercent = percent * categoryWeights[i] / 100;
			estimateReport += String.format(
					"%7.2f%% = %5.2f%% of %2.0f%% for %s\n",
					weightPercent, percent, categoryWeights[i], cat);
			weightedPercentTotal += weightPercent;
		}
		
		estimateReport += "--------------------------------\n";
		estimateReport += String.format("%7.2f%% weighted percent\n", 
				weightedPercentTotal);
		estimateReport += "Letter Grade Estimate: ";
		
		// pick a suitable grade
		for (int i = 0; i < miniThresholds.length; i++)
			if (weightedPercentTotal >= miniThresholds[i])
				return estimateReport += letterGrades[i];
		
		return estimateReport += 
			"unable to estimate letter grade for " + weightedPercentTotal;
	}
	
	/**
	 * A method to check and parse the file. The error is indicated in 
	 * exception message. The method checks for:
	 * 	<li>1. if each of the line is legal
	 * 	<li>2. if grades correspond to thresholds
	 * 	<li>3. if thresholds are in decreasing order
	 * 	<li>4. if weights add up 100
	 * 	<li>5. if weights correspond to categories
	 * 	<li>6. if the category does not exist for an assignment
	 * 
	 * @param stdIn - the Scanner for input
	 * @throws FileNotFoundException if the file is not found
	 * @throws GradeFileFormatException if the file is corrupted
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
			// choose parser
			if (i == 0 || i == 2)
				pattern = PTRN_WORDS;
			else if (i == 1 || i == 3)
				pattern = PTRN_NUMBS;
			else
				pattern = PTRN_ASGNS;
			
			// if matches, split to get info;
			if (pattern.matcher(tmp).matches()) {
				String[] splitStrings = PTRN_SPLIT.split(tmp);
				switch(i) {
				case 0: // grades line
					grades = splitStrings;
					break;
					
				case 1: // thresholds line
					thresholds = new double[splitStrings.length];
					for (int j = 0; j < splitStrings.length; j++)
						thresholds[j] = Double.parseDouble(splitStrings[j]);
					break;
					
				case 2: // categories line
					categories = splitStrings;
					break;
				
				case 3: // weights line
					weights = new double[splitStrings.length];
					for (int j = 0; j < splitStrings.length; j++)
						weights[j] = Double.parseDouble(splitStrings[j]);
					break;
				
				default: // any other assignment lines
					// check if the category exists
					boolean found = false;
					for (String cat : categories)
						if (splitStrings[0].charAt(0) == cat.charAt(0))
							found = true;
					if (!found)
						throw new GradeFileFormatException(
							"Unknown Assignment Category: " + splitStrings[0]);
					
					// parse and add assignment
					double earned = Double.parseDouble(splitStrings[1]);
					double max = Double.parseDouble(splitStrings[2]);
					scores.add(new Score(splitStrings[0], earned, max));
				}
			}
			// if this line does not match format
			else
				throw new GradeFileFormatException(
						"Bad Line (" + i + "): " + tmp);
			i++;
		}
		
		// check for header line #
		if (i < 4)
			throw new GradeFileFormatException(
					"Unexpected File Header End: " + tmp);
		
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
		
		// update fields
		letterGrades = grades;
		miniThresholds = thresholds;
		categoryNames = categories;
		categoryWeights = weights;
	}
}
