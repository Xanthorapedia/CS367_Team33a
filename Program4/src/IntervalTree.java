import java.util.LinkedList;
import java.util.List;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {


	private IntervalNode<T> root;
	private List<IntervalADT<T>> list;



	@Override
	public IntervalNode<T> getRoot() {
		return root;

	}

	@Override
	public void insert(IntervalADT<T> interval)
			throws IllegalArgumentException {
		if(interval == null)
			throw new IllegalArgumentException();
		root = insertHelper(root,interval);

	}
	private IntervalNode<T> insertHelper(IntervalNode<T> node,
			IntervalADT<T> interval){

		//base case 
		if(node == null) return new IntervalNode<T> (interval);
		
		
		if(interval.compareTo(node.getInterval()) == 0) throw new IllegalArgumentException();
		
		if(interval.compareTo(node.getInterval()) < 0){

			//search right subtree and update maxend
			IntervalNode<T> newnode = insertHelper(node.getRightNode(),interval);
			node.setRightNode(newnode);
			//compare new node's maxend  with root's
			if (newnode.getMaxEnd().compareTo(root.getMaxEnd()) > 0)
				root.setMaxEnd(newnode.getMaxEnd());

		}
		else if( interval.compareTo(node.getInterval()) > 0){
			//search left subtree and update MaxEnd 
			IntervalNode<T> newnode = insertHelper(node.getLeftNode(),interval);
			node.setLeftNode(newnode);
			//compare new node's maxend with root's
			if (newnode.getMaxEnd().compareTo(root.getMaxEnd()) > 0)
				root.setMaxEnd(newnode.getMaxEnd());
		
		}
		return node;
	}

	@Override
	public void delete(IntervalADT<T> interval)
			throws IntervalNotFoundException, IllegalArgumentException {
		if (interval == null)
			throw new IllegalArgumentException();
		root = deleteHelper(root,interval);

	}

	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
			IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException{


		if (interval == null)
			throw new IllegalArgumentException();
		// base case
		if (node == null)
			throw new IntervalNotFoundException(interval.toString());
		if (node.getInterval().compareTo(interval) == 0){
			// delete the  node
			if (node.getLeftNode() == null && node.getRightNode() == null){
				// case 1: this node have no child
				return null;
			} else if (node.getLeftNode() != null && node.getRightNode() == null){
				// the node only have left child
				return node.getLeftNode();
			} else if (node.getLeftNode() == null && node.getRightNode() != null){
				// the node only have right child
				return node.getRightNode();
			} else{
				// the node have two child
				// Replace the node's interval with the in-order successor interval. 
				node.setInterval(node.getSuccessor().getInterval());
				// Call deleteHelper() on the in-order successor node of the right subtree.
				deleteHelper(node.getRightNode(), node.getInterval());
			}
		} else if (node.getInterval().compareTo(interval) > 0){

			node.setLeftNode(deleteHelper(node.getLeftNode(),interval));
		} else if (node.getInterval().compareTo(interval) < 0){

			node.setRightNode(deleteHelper(node.getRightNode(),interval));

		}
		//update the max end
		if (node.getMaxEnd().compareTo(interval.getEnd()) == 0){
			T end = node.getInterval().getEnd();
           
			if (node.getLeftNode() == null && node.getRightNode() == null){
				node.setMaxEnd(end);
			
			} else if (node.getLeftNode() != null && node.getRightNode() == null){
				//compare left node's maxend with node's
				if (node.getLeftNode().getMaxEnd().compareTo(end) > 0)
					node.setMaxEnd(node.getLeftNode().getMaxEnd());
				else
					node.setMaxEnd(end);
			} else if (node.getLeftNode() == null && node.getRightNode() != null){
				if (node.getRightNode().getMaxEnd().compareTo(end) > 0)
					node.setMaxEnd(node.getRightNode().getMaxEnd());
				else
					node.setMaxEnd(end);
			} else{
				if (node.getLeftNode().getMaxEnd().compareTo(end) > 0){
					if (node.getLeftNode().getMaxEnd().compareTo(node.getRightNode().getMaxEnd()) > 0)
						node.setMaxEnd(node.getLeftNode().getMaxEnd());
					else
						node.setMaxEnd(node.getRightNode().getMaxEnd());
				}
				else{
					if (node.getRightNode().getMaxEnd().compareTo(end) > 0)
						node.setMaxEnd(node.getRightNode().getMaxEnd());
					else
						node.setMaxEnd(end);
				}
			}


		}
		return node;
	}


	
	@Override
	public List<IntervalADT<T>> findOverlapping(IntervalADT<T> interval) {
		if (interval == null)
			throw new IllegalArgumentException();
		list = new LinkedList<IntervalADT<T>>();
		findOverlappingHelper(root, interval, list);
		return list;
	}

	private void findOverlappingHelper(IntervalNode<T> node, IntervalADT<T> interval, List<IntervalADT<T>> result){
		if(node == null) return;
		if(node.getInterval().overlaps(interval)) list.add(root.getInterval());
		if(node.getLeftNode().getMaxEnd().compareTo(interval.getStart()) >= 0)
			findOverlappingHelper(node.getLeftNode(),interval,result);
		if(node.getRightNode().getMaxEnd().compareTo(interval.getStart()) >= 0)
			findOverlappingHelper(node.getRightNode(),interval,result);

	}
	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		if (point == null)
			throw new IllegalArgumentException();
		list = new LinkedList<IntervalADT<T>>();
		searchPointHelper(root, point, list);
		return list;
	}
	
	
	/*
	 * Traverse the tree and add the overlapping interval
	 */
	private void searchPointHelper(IntervalNode<T> node, T point, List<IntervalADT<T>> result ){
		if(node == null) return;
		else{
			searchPointHelper(node.getLeftNode(),point,list);
			//No need to search right subtree if the point is less than start
			if(point.compareTo(node.getInterval().getStart()) >= 0)
				searchPointHelper(node.getRightNode(),point,list);
			if(node.getInterval().contains(point))
				list.add(root.getInterval());
			return;
		}


	}

	@Override
	public int getSize() {
		return sizeHelper(root);
	}
	
	private int sizeHelper(IntervalNode<T> node){
		if(node == null) return 0;
		else 
			return sizeHelper(node.getLeftNode()) + sizeHelper(node.getRightNode()) + 1;
	}

	@Override
	public int getHeight() {
		return getHeightHelper(root);
	}

	private int getHeightHelper(IntervalNode<T> intervalNode){
		//if it is an empty tree
		if(intervalNode == null) 
			return 0;
	   return Math.max(getHeightHelper(intervalNode.getLeftNode()), getHeightHelper(intervalNode.getRightNode())) + 1;

	}
	
	@Override
	public boolean contains(IntervalADT<T> interval) {
		if (interval == null)
			throw new IllegalArgumentException();
		return containsHelper(interval,root);
	}
	/*
	 * Helper method for contains- traverse through all the nodes and compare the interval 
	 * to see if the node already exist
	 */
	private boolean containsHelper(IntervalADT<T> thisinterval, IntervalNode<T> node){
		if(root == null) return false;
		else return containsHelper(thisinterval,node.getLeftNode()) || containsHelper(thisinterval,node.getRightNode())
				||root.getInterval().compareTo(thisinterval) == 0;	

	}

	@Override
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + getHeight());
		System.out.println("Size: " + getSize());
		System.out.println("-----------------------------------------");

	}

}
