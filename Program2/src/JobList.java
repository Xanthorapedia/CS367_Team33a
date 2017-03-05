import java.util.Iterator;

/**
 * This class is JobList with usage of ListNode, and implements the ListADT and
 * Iterable.
 * 
 * @author Meredith Lou, Boby Lv, Dasong Gao
 *
 */
public class JobList implements ListADT<Job> {

	/** reference to the header node of the ListNode of JobList */
	private Listnode<Job> head;
	/** tracking the current number of Items in the ListNode of JobList */
	private int numItems;

	/**
	 * Constructor for instantiating the JobList Class
	 */
	public JobList() {
		head = new Listnode<Job>(null);
		numItems = 0;
	}

	/**
	 * Constructor for instantiating the Iterator of JobList
	 * 
	 * @return A JobList Iterator with indirect access to JobList
	 */
	@Override
	public Iterator<Job> iterator() {
		return new JobListIterator(head);
	}

	/**
	 * Add new job item to the last position of ListNode by creating a node to
	 * the ListNode.
	 * 
	 * @throws IllegalArgumentException if the item in the item is null.
	 * @param item the Job Item that being added
	 */
	@Override
	public void add(Job item) {
		add(numItems, item);
	}

	/**
	 * Add new job Item to the indicated position of ListNode by creating a node
	 * to the ListNode
	 * 
	 * @throws IllegalArgumentException if the item in the parameter is null
	 * @throws IndexOutOfBoundsException if the position in parameter is not 
	 * 			valid
	 * @param pos
	 *            the indicated position of the ListNode that will be added
	 * @param item
	 *            the Job Item that will be added
	 */
	@Override
	public void add(int pos, Job item) {
		// argument check
		if (item == null) {
			throw new IllegalArgumentException();
		}
		if (pos < 0 || pos > numItems) {
			throw new IndexOutOfBoundsException(": " + pos);
		}

		Listnode<Job> newNode = new Listnode<Job>(item);

		Listnode<Job> cur = head;
		for (int curr = 0; curr < pos; curr++) {
			cur = cur.getNext();
		}
		newNode.setNext(cur.getNext());
		cur.setNext(newNode);

		numItems++;
	}

	/**
	 * This method determine weather this ListNode has the indicated Job Item by
	 * go through all nodes in this list, and determine by the equals method of
	 * Object class.
	 * 
	 * @throws IllegalArgumentException if the item in the parameter is null
	 * @return true when this JobList contains this indicated item, false otherwise
	 */
	@Override
	public boolean contains(Job item) {

		if (item == null) {
			throw new IllegalArgumentException();
		}
		for (Job e : this) {
			if (item.equals(e)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * This method is used to get the Item in the indicated position, by
	 * creating a curr item and go through every items before wanted item to get
	 * this item.
	 * 
	 * @param pos
	 *            the position of wanted Item
	 * @throws IndexOutOfBoundsException if the position in parameter is not 
	 * 				valid
	 * @return the data of wanted position
	 */
	@Override
	public Job get(int pos) {
		if (pos < 0 || pos >= numItems) {
			throw new IndexOutOfBoundsException();
		}
		Listnode<Job> curr = head.getNext();
		for (int i = 0; i < pos; i++) {
			curr = curr.getNext();
		}

		return curr.getData();
	}

	/**
	 * this method determines weather this JobList is empty by checking the
	 * value of numItems
	 * 
	 * @return true if the list is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return numItems == 0;
	}

	/**
	 * This method removes the Item at the indicated position by creating a curr
	 * item and go through every items before wanted item to get this item. And
	 * then store this item in a temperate item then return this removed item
	 * 
	 * @param pos the position of the item that will be removed
	 * @throws IndexOutOfBoundsException if the position in parameter is not 
	 * 			valid
	 * @return the item that has been removed
	 */
	@Override
	public Job remove(int pos) {
		if (pos < 0 || pos >= numItems) {
			throw new IndexOutOfBoundsException();
		}

		Listnode<Job> curr = head;
		for (int i = 0; i < pos; i++) {
			curr = curr.getNext();
		}
		Job item = curr.getNext().getData();
		curr.setNext(curr.getNext().getNext());
		numItems--;

		return item;
	}

	/**
	 * This method determines the size of this list by return the value of
	 * numItems, which equals to size of this list
	 * 
	 * @return numItems which equals to size of this list
	 */
	@Override
	public int size() {

		return numItems;
	}

}
