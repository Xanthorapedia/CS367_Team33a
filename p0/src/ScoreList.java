/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p0
// FILE:             ScoreList.java
// 	                 Score.java 
// 	                 ScoreListADT.java
// 	                 Test_ScoreList.java
//
// Author1: Dasong Gao, dgao24@wisc.edu, 9075308438, 001
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: None
// 
// Online sources: None
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class implements a container which keeps Score objects in a indexed
 * list.
 * 
 * @author Dasong Gao
 *
 */
public class ScoreList implements ScoreListADT{
	
	/** The Score array used to store references to Score objects.*/
	private Score[] list;
	
	/** The number of elements in the list.*/
	private int numElements;

	/**
	 * Creates a ScoreList with the initial capacity of 100 Score objects.
	 */
	public ScoreList() {
		list = new Score[100];
		numElements = 0;
	}

	/** 
	 * Returns the number of Scores in the list or zero
	 * @return the number of scores in this list
	 */
	@Override
	public int size() {
		return numElements;
	}

	/** 
	 * Adds the score to the end of this list.
	 * 
	 * @param s a non-null Score to place as the last item in the list.
	 * @throws IllegalArgumentException
	 */
	@Override
	public void add(Score score) throws IllegalArgumentException {
		// argument check
		if (score == null)
			throw new IllegalArgumentException();
		
		// if full, expand
		if (numElements == list.length)
			expandArray();
		
		list[numElements++] = score;
	}

	/**
	 * Removes and returns the item at index position i.
	 * If i is less than zero or greater than size()-1,
	 * will throw an IndexOutOfBoundsException.
	 * @param i must be greater than or equal to zero and less than size()
	 * @return the item at index i
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public Score remove(int index) throws IndexOutOfBoundsException {
		// argument check
		if (index < 0 || index > numElements - 1)
			throw new IndexOutOfBoundsException();
		
		// mark the removed element
		Score rmvdE = list[index];
		numElements--;
		
		// shift each element to fill the gap
		for (int i = index; i < numElements; i++)
			list[i] = list[i + 1];
		
		return rmvdE;
	}

	/**
	 * Returns (without removing) the item at index position i.
	 * If i is less than zero or greater than size()-1,
	 * will throw an IndexOutOfBoundsException.
	 * @param i must be greater than or equal to zero and less than size()
	 * @return the item at index i
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public Score get(int index) throws IndexOutOfBoundsException {
		// argument check
		if (index < 0 || index > numElements - 1)
			throw new IndexOutOfBoundsException();
		
		return list[index];
	}

	/**
	 * Double the length of the array for storing Score elements.
	 * Elements are kept in place during the operation.
	 */
	private void expandArray() {
		Score[] oldList = list;
		list = new Score[list.length * 2];
		
		for (int i = 0; i < numElements; i++)
			list[i] = oldList[i];
	}

}
