import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * An simple method to tell whether the data file is valid and parse if it is.
 * I used regular expression (regex) for format check. You can change data.txt
 * to see what happens if the file is corrupted.
 * See if we can use this in P1.
 * @author Dasong
 *
 */
public class TestParse {
	
	private static final String FILE_PATH = "data.txt";

	public static void main(String[] args) {
		try {
			checkFile(FILE_PATH);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
	public static void checkFile(String path) throws FileNotFoundException, Exception {
		// Patterns used for splitting and checking for formats
		final Pattern PTRN_SPLIT = Pattern.compile("\\s+|(#.*)+");
		final Pattern PTRN_WORDS = 
				Pattern.compile("\\s*[a-zA-Z]+(\\s+[a-zA-Z]+)*\\s*(#.*)*");
		final Pattern PTRN_NUMBS = 
				Pattern.compile("\\s*\\d+(\\s+\\d+)*\\s*(#.*)*");
		final Pattern PTRN_ASGNS = 
				Pattern.compile("\\s*[a-zA-Z]\\d*(\\s+\\d+){2}\\s*(#.*)*");
		
		// file input and current line
		Scanner stdIn = new Scanner(new File(path));
		String tmp = null;
		
		// all grade letters, corresponding thresholds, 
		// category of assignments and corresponding weights
		String[] grades = null;
		int[] thresholds = null;
		String[] categories = null;
		int[] weights = null;
		
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
					thresholds = new int[splitStrings.length];
					for (int j = 0; j < splitStrings.length; j++)
						thresholds[j] = Integer.parseInt(splitStrings[j]);
				}
				// categories line
				else if (i == 2)
					categories = splitStrings;
				// weights line, parse thresholds and put into int[]
				else if (i == 3) {
					weights = new int[splitStrings.length];
					for (int j = 0; j < splitStrings.length; j++)
						weights[j] = Integer.parseInt(splitStrings[j]);
				}
				// any other assignment lines
				else {
					String[] assgn = splitStrings;
					// TODO add to score here:
					// [0] = assignment name, [1] = points earned, [2] = points possible
				}
			}
			else {
				stdIn.close();
				throw new Exception("Bad line (" + i + "): " + tmp);
			}
			
			i++;
		}
		stdIn.close();
		
		// #grades should = #thresholds
		if (grades.length != thresholds.length) {
			throw new Exception("Grades Thresholds Mismatch: " 
					+ grades.length + ":" + thresholds.length);
		}
		
		// thresholds should be in decreasing order
		for (int j = 0; j < thresholds.length - 1; j++)
			if (thresholds[j] < thresholds[j + 1])
				throw new Exception("Thresholds out of Order: " 
						+ thresholds[j] + " " + thresholds[j + 1]);
		
		// #weights should = #categories
		if (categories.length != weights.length)
			throw new Exception("Categories Weights Mismatch: " 
					+ categories.length + ":" + weights.length);
		
		// weights should add up 100
		int sum = 0;
		for (int j = 0; j < weights.length; j++)
			sum += weights[j];
		if (sum != 100)
			throw new Exception("Bad Weights: sum = " + sum);
		
		
		stdIn.close();
	}

}
