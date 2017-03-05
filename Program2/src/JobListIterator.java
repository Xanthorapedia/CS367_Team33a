/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p2 Welcome to the Job Market
// FILE:             JobListIterator.java
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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class implements the Job iterator for the JobList Class.
 * The remove() method is not used.
 * 
 * @author Dasong Gao
 *
 */
public class JobListIterator implements Iterator<Job> {

	/** the current Listnode*/
	private Listnode<Job> cur;
	
	/**
	 * The constructor creates a iterator of JobList.
	 * @param header - the header node of the list
	 */
	public JobListIterator(Listnode<Job> header) {
		cur = header;
	}

	/**
	 * The method checks if there is a next element in the list.
	 * @return true if there is a next element, false if not.
	 */
	@Override
	public boolean hasNext() {
		return cur.getNext() != null;
	}

	/**
	 * The method returns the next element of the current one.
	 * @return next Job element in the list.
	 * @throws NoSuchElementException if the iteration has no more Job elements
	 */
	@Override
	public Job next() {
		if (!hasNext())
			throw new NoSuchElementException();
		cur = cur.getNext();
		return cur.getData();
	}
	
	/**
	 * The remove() method is not used.
	 * @throws UnsupportedOperationException when the method called.
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
