
public class MyTeam {
	// 1. Work together to decide upon a team nickname. What is your team's nick
	// name?
	// Team 33
	// 2. Who are the members (name and email for each) of your team?
	// Name Email Years
	// Maggie Mbuday@wisc.edu 1
	// dasong dgao24@wisc.edu 3
	// Sam sruh@wisc.edu 4
	// bobby zlv7@wisc.edu 1
	// apoorva dhawan3@wisc.edu 1
	// meredith ylou9@wisc.edu
	//
	//
	// 3. What is the sum of years of programming (in any language) of your team
	// members?
	// Include the number of years for each teammate in the table above.
	//
	//
	// 10 years total
	//
	//
	// 4. What is the sum of all the multiples of 3 and 7 below 100?
	// Example: sum of all multiples of 3 and 4 below 40 is: 12+24+36 = 72
	// (problem taken from ProjectEuler.net -- check it out if you like)
	//
	// 210
	//
	// 5. Complete the method below, put it in a Java class, and call it to see
	// if you are correct.

	/**
	 * Returns the sum of multiples of x and y that are less than z.
	 *
	 * PRE-CONDITIONS: x,y, and z are all positive integers greater than zero x
	 * and y are both less than z
	 *
	 * POST-CONDITIONS: The computed value is returned.
	 */
	public static int sumOfMultiples(int x, int y, int z) {
		int total = 0;

		for (int i = 0; i < z; i++) {
			if (i % x == 0 && i >= x && i % y == 0 && i >= y)
				total += i;
		}
		return total;
	}

	// 6. Include at least 3 other examples of calling your method to compute
	// such a sum.
	// x = 3, y = 10, z = 100 Answer: 180
	// x = 3, y = 5, z = 100 Answer: 315
	// x = 3, y = 4, z = 100 Answer: 432
	//
	// 7. Can you find any positive integer values that meet the
	// pre-conditions, but fail your method at run-time? If so, what are they?
	// FAILS FOR: x = y = z =
	public static void main(String[] args) {
		System.out.println(sumOfMultiples(1, 1, 100));
	}
}
