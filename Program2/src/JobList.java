import java.util.Iterator;

/**
 * This class is JobList with usage of ListNode, and implements the ListADT and
 * Iterable.
 * 
 * @author ( ), Bobby Lv
 *
 */
public class JobList implements ListADT<Job> {

	private Listnode<Job> head; // reference to the header node of the ListNode
								// of JobList
	private int numItems; // tracking the current number of Items in the
							// ListNode of JobList

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
	 * Add new job Item to the last position of ListNode by creating a node to
	 * the ListNode.
	 * 
	 * @exception IllegalArgumentException()
	 *                this Exception will be thrown if the item in the parameter
	 *                is null.
	 * @param item
	 *            the Job Item that will be added
	 */
	@Override
	public void add(Job item) {
		if (item == null)
			throw new IllegalArgumentException();
		Listnode<Job> newnode = new Listnode<Job>(item);
		Listnode<Job> node = head;
		while (node.getNext() != null)
			node = node.getNext();
		node.setNext(newnode);

		numItems++;

	}

	/**
	 * Add new job Item to the indicated position of ListNode by creating a node
	 * to the ListNode
	 * 
	 * @exception IllegalArgumentException()
	 *                this Exception will be thrown if the item in the parameter
	 *                is null
	 * @exception IndexOutOfBoundsException()
	 *                this Exception will be thrown if the position in parameter
	 *                is not valid
	 * @param pos
	 *            the indicated position of the ListNode that will be added
	 * @param item
	 *            the Job Item that will be added
	 */
	@Override
	public void add(int pos, Job item) {

		if (item == null) {
			throw new IllegalArgumentException();
		}

		if (pos < 0 || pos > numItems) {
			throw new IndexOutOfBoundsException();
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
	 * @exception IllegalArgumentException()
	 *                this Exception will be thrown if the item in the parameter
	 *                is null
	 * @return true when this JobList contains this indicated item
	 * @return false when this JobList doesn't contain this indicated item
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
	 * @exception IndexOutOfBoundsException()
	 *                this Exception will be thrown if the position in parameter
	 *                is not valid
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
	 * @return true if the numItems equals zero, which means this list is empty
	 * @return false if the numItems has a value that doesn't equal to zero
	 */
	@Override
	public boolean isEmpty() {
		return numItems == 0; // return true if the list is empty
	}

	/**
	 * this method removes the Item at the indicated position by creating a curr
	 * item and go through every items before wanted item to get this item. And
	 * then store this item in a temperate item then return this removed item
	 * 
	 * @param the
	 *            position of the item that will be removed
	 * @exception IndexOutOfBoundsException()
	 *                this Exception will be thrown if the position in parameter
	 *                is not valid
	 * @return the item that has been removed
	 */
	@Override
	public Job remove(int pos) {
		if (pos < 0 || pos >= numItems) {
			throw new IndexOutOfBoundsException();
		}

		Listnode<Job> curr = new Listnode<Job>(null);
		curr = head;
		for (int i = 0; i < pos; i++) {
			curr = curr.getNext();
		}
		Job item = curr.getNext().getData();
		curr.setNext(curr.getNext().getNext());
		numItems--;

		return item;
	}

	/**
	 * this method determines the size of this list by return the value of
	 * numItems, which equals to size of this list
	 * 
	 * @return numItems which equals to size of this list
	 */
	@Override
	public int size() {

		return numItems;
	}

}
