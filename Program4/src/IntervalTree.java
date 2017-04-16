import java.util.ArrayList;
import java.util.List;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {

	/** the root node of this tree*/
	private IntervalNode<T> root;

	/**
	 * Constructs a <code>IntervalTree</code> whose root is specified by the
	 * argument.
	 * @param root the root node of the tree
	 */
	public IntervalTree(IntervalNode<T> root) {
		this.root = root;
	}
	
	/**
	 * Constructs a initially empty <code>IntervalTree</code>.
	 */
	public IntervalTree() {
		root = null;
	}

	@Override
	public IntervalNode<T> getRoot() {
		return root;
	}

	@Override
	public void insert(IntervalADT<T> interval) throws IllegalArgumentException {
		if(interval == null)
			throw new IllegalArgumentException();
		root = insertHelper(root, interval);

	}
	
	/**
	 * The helper method for inserting a new node into the tree.
	 * @param node the root of the subtree to insert to
	 * @param interval the interval to be inserted into the subtree
	 * @return
	 */
	private IntervalNode<T> insertHelper(IntervalNode<T> node,
			IntervalADT<T> interval){

		IntervalNode<T> newnode;
		
		// at leaf
		if(node == null) 
			return new IntervalNode<T>(interval);
		
		// search left
		if(interval.compareTo(node.getInterval()) < 0){
			//search right subtree and update maxEnd
			newnode = insertHelper(node.getLeftNode(), interval);
			node.setLeftNode(newnode);
		}
		// search right
		else if( interval.compareTo(node.getInterval()) > 0){
			newnode = insertHelper(node.getRightNode(), interval);
			node.setRightNode(newnode);
		}
		// duplicate interval
		else
			throw new IllegalArgumentException();
		
		// update maxEnd
		if (newnode.getMaxEnd().compareTo(node.getMaxEnd()) > 0)
			node.setMaxEnd(newnode.getMaxEnd());
		
		return node;
	}

	@Override
	public void delete(IntervalADT<T> interval)
			throws IntervalNotFoundException, IllegalArgumentException {
		if (interval == null)
			throw new IllegalArgumentException();
		root = deleteHelper(root, interval);

	}

	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
			IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException{

		if (interval == null)
			throw new IllegalArgumentException();
		if (node == null)
			throw new IntervalNotFoundException(interval.toString());
		
		// delete left
		if (interval.compareTo(node.getInterval()) < 0)
			node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
		// delete right
		else if (interval.compareTo(node.getInterval()) > 0)
			node.setRightNode(deleteHelper(node.getRightNode(), interval));
		else {
			// if exists right child
			if (node.getRightNode() != null) {
				IntervalADT<T> suc = node.getSuccessor().getInterval();
				node.setInterval(suc);
				node.setRightNode(deleteHelper(node.getRightNode(), suc));
			}
			else
				return node.getLeftNode();
		}
		
		// update maxEnd with children
		IntervalNode<T> lChild = node.getLeftNode();
		IntervalNode<T> rChild = node.getRightNode();
		// selects the biggest child
		T childMax = null;
		if (lChild != null && rChild != null) {
			T lEnd = lChild.getMaxEnd();
			T rEnd = rChild.getMaxEnd();
			childMax = lEnd.compareTo(rEnd) > 0 ? lEnd : rEnd;
		}
		else if (lChild != null && rChild == null)
			childMax = lChild.getMaxEnd();
		else if (lChild == null && rChild != null)
			childMax = rChild.getMaxEnd();
		// inherit maxEnd from biggest child or itself
		T nodeEnd = node.getInterval().getEnd();
		node.setMaxEnd(childMax != null && childMax.compareTo(nodeEnd) > 0 ?
				childMax : nodeEnd);
		
		return node;
	}


	
	@Override
	public List<IntervalADT<T>> findOverlapping(IntervalADT<T> interval) {
		if (interval == null)
			throw new IllegalArgumentException();
		ArrayList<IntervalADT<T>> list = new ArrayList<IntervalADT<T>>();
		findOverlappingHelper(root, interval, list);
		return list;
	}

	private void findOverlappingHelper(IntervalNode<T> node,
			IntervalADT<T> interval, List<IntervalADT<T>> result){
		if(node == null)
			return;
		if(node.getInterval().overlaps(interval))
			result.add(node.getInterval());

		IntervalNode<T> child = node.getLeftNode();
		if(child != null && child.getMaxEnd().compareTo(interval.getStart()) >= 0)
			findOverlappingHelper(node.getLeftNode(), interval, result);
		child = node.getRightNode();
		if(child != null && child.getMaxEnd().compareTo(interval.getStart()) >= 0)
			findOverlappingHelper(node.getRightNode(), interval, result);
	}
	
	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		if (point == null)
			throw new IllegalArgumentException();
		ArrayList<IntervalADT<T>> list = new ArrayList<IntervalADT<T>>();
		searchPointHelper(root, point, list);
		return list;
	}
	
	/**
	 * Traverse the tree and add the overlapping interval
	 */
	private void searchPointHelper(IntervalNode<T> node, T point, 
			List<IntervalADT<T>> result ){
		if(node == null)
			return;

		if(node.getInterval().contains(point))
			result.add(node.getInterval());
		
		IntervalNode<T> child = node.getLeftNode();
		if(child != null && child.getMaxEnd().compareTo(point) >= 0)
			searchPointHelper(node.getLeftNode(), point, result);
		child = node.getRightNode();
		if(child != null && child.getMaxEnd().compareTo(point) >= 0)
			searchPointHelper(node.getRightNode(), point, result);
	}

	@Override
	public int getSize() {
		return sizeHelper(root);
	}
	
	private int sizeHelper(IntervalNode<T> node){
		if(node == null)
			return 0;
		else 
			return sizeHelper(node.getLeftNode()) + 
					sizeHelper(node.getRightNode()) + 1;
	}

	@Override
	public int getHeight() {
		return getHeightHelper(root);
	}

	private int getHeightHelper(IntervalNode<T> intervalNode){
		if(intervalNode == null) 
			return 0;
	   return Math.max(getHeightHelper(intervalNode.getLeftNode()), 
			   getHeightHelper(intervalNode.getRightNode())) + 1;

	}
	
	@Override
	public boolean contains(IntervalADT<T> interval) {
		if (interval == null)
			throw new IllegalArgumentException();
		return containsHelper(interval, root);
	}
	/**
	 * Helper method for contains- traverse through all the nodes and compare
	 * the interval to see if the node already exist
	 */
	private boolean containsHelper(IntervalADT<T> thisinterval,
			IntervalNode<T> node) {
		if(node == null)
			return false;
		return containsHelper(thisinterval,node.getLeftNode()) ||
				containsHelper(thisinterval,node.getRightNode()) ||
				node.getInterval().compareTo(thisinterval) == 0;	

	}

	@Override
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + getHeight());
		System.out.println("Size: " + getSize());
		System.out.println("-----------------------------------------");

	}

}
