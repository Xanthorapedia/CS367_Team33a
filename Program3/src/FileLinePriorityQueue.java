import java.util.Comparator;

/**
 * An implementation of the MinPriorityQueueADT interface. This implementation stores FileLine objects.
 * See MinPriorityQueueADT.java for a description of each method. 
 *
 */
public class FileLinePriorityQueue implements MinPriorityQueueADT<FileLine> {
    /** the comparator for comparing lines */
    private Comparator<FileLine> cmp;
    /** the max size of the queue */
    private int maxSize;
    /** the internal array for storing FileLines */
    private FileLine[] lines;
    /** element count */
    private int itemCount;
    
//TODO file header
    public FileLinePriorityQueue(int initialSize, Comparator<FileLine> cmp) {
		this.cmp = cmp;
		maxSize = initialSize;
		
		lines = new FileLine[maxSize + 1];
		itemCount = 0;
    }

    public FileLine removeMin() throws PriorityQueueEmptyException {
		if (itemCount == 0)
			throw new PriorityQueueEmptyException();
		
		// returned line
		FileLine ret = lines[1];
		swap(1, itemCount);
		lines[itemCount--] = null;
		
		// heapify till the end
		int cur = 1;
		while ((cur = reheapify(cur)) > 0);

		return ret;
    }

    public void insert(FileLine fl) throws PriorityQueueFullException {
		if (itemCount == maxSize)
			throw new PriorityQueueFullException();
		
		lines[++itemCount] = fl;
		
		// heapify till the top
		int cur = itemCount;
		while (reheapify(cur /= 2) > 0);
    }

    public boolean isEmpty() {
		return itemCount == 0;
    }
    
    /**
     * Swaps two elements of the line array
     * @param i the first index to be swapped
     * @param j the second index to be swapped
     */
    private void swap(int i, int j) {
    	FileLine tmp = lines[i];
    	lines[i] = lines[j];
    	lines[j] = tmp;
    }
    
    /**
     * Compare the parent at index with its children to adjust the three ndoes
     * so that parent <= lchild <= rchild
     * @param parent the index of the parent
     * @return next index to be altered, 0 if done
     */
    private int reheapify(int parent) {
    	// cannot reheapify 0
    	if (parent == 0)
    		return 0;
    	
    	int lChild = parent * 2;
    	int rChild = parent * 2 + 1;
    	int next = 0;
    	
    	// if rChild exists and lChild > rChild, swap
    	if (rChild <= itemCount && cmp.compare(lines[lChild], lines[rChild]) > 0) {
    		swap(lChild, rChild);
    		next = rChild;
    	}
    	
    	// if lChild exists and parent > lChild, swap
    	if (lChild <= itemCount && cmp.compare(lines[parent], lines[lChild]) > 0) {
    		swap(parent, lChild);
    		next = lChild;
    	}
    	
    	return next;
    }
}
