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
