/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p4 Schedule Planner
// FILE:             Interval.java
//
// TEAM:    team 33a
// Authors: team 33a members
// Author0: Dasong Gao,		dgao24@wisc.edu,	dgao24,		lec001
// Author1: Meredith Lou,	ylou9@wisc.edu,		ylou9,		lec001
// Author2: Bobby Lv,		zlv7@wisc.edu,		zlv7,		lec001
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: none.
// 
// Online sources: none.
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class implements a representation of time interval as described in
 * <code>IntervalADT</code>, which records the interval's start, end and label.
 * Two instances of this class can be compared according to their starts and
 * ends. The comparison will be first the 'start' value and then if 'start' are
 * same, the 'end' values will be compared.
 * 
 * @author Dasong Gao
 *
 * @param <T>
 *            the type of interval start and end
 */
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

	/** start and end of the interval */
	T start, end;
	/** the label of the interval */
	String label;

	/**
	 * Constructs a Interval instance with the given start, end and label.
	 * 
	 * @param start
	 *            the start of the interval
	 * @param end
	 *            the end of the interval
	 * @param label
	 *            the label of the interval
	 */
	public Interval(T start, T end, String label) {
		this.start = start;
		this.end = end;
		this.label = label;
	}

	@Override
	public T getStart() {
		return start;
	}

	@Override
	public T getEnd() {
		return end;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public boolean overlaps(IntervalADT<T> other) {
		return start.compareTo(other.getEnd()) <= 0 &&
				end.compareTo(other.getStart()) >= 0;
	}

	@Override
	public boolean contains(T point) {
		return start.compareTo(point) <= 0 && point.compareTo(end) <= 0;
	}

	@Override
	public int compareTo(IntervalADT<T> other) {
		int ds = start.compareTo(other.getStart());
		return ds != 0 ? ds : end.compareTo(other.getEnd());
	}

}
