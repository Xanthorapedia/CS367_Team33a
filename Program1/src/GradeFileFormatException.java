/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p1 Grade Estimator
// FILE:             GradeFileFormatException.java
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
// Online sources: none.
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This is an exception type signalizing an error in the file fed into
 * GradeEstimator. Errors include:
 * 	<li>1. some line being legal
 * 	<li>2. no enough grades for thresholds (or the reverse)
 * 	<li>3. disordered thresholds
 * 	<li>4. weights failing to add up 100
 * 	<li>5. no enough weights for categories (or the reverse)
 * 	<li>6. adding an assignment of an unknown category
 *
 * @author Team 33 Members
 */
@SuppressWarnings("serial")
public class GradeFileFormatException extends Exception {

	public GradeFileFormatException() {
		super();
	}

	public GradeFileFormatException(String msg) {
		super(msg);
	}
}
