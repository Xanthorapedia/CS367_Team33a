/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p4 Schedule Planner
// FILE:             IntervalNode.java
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
 * This class defines the IntervalNode for the IntervalTree. This node has three
 * components: 1) interval - the data that we want to store in this node 2)
 * maxEnd - this represents the maximum end of any interval stored in the tree
 * rooted at this node 3) leftNode and rightNode - the left and right node
 * references in the IntervalTree.
 * 
 * This class will be used while constructing the IntervalTree.
 *
 * @param <T>
 *            the template parameter for the data field - interval.
 */
public class IntervalNode<T extends Comparable<T>> {
	
	/** Interval stored in the node. */
	private IntervalADT<T> interval;

	/** Each node stores the maxEnd of the interval in its subtree. */
	private T maxEnd;

	/** LeftNode and RightNode. */
	private IntervalNode<T> leftNode, rightNode;

	/**
	 * Constructor to create a new IntervalNode. Set the interval data structure
	 * present as member variable above and maxEnd associated with the interval.
	 * Hint: Use interval.getEnd() to get the end of the interval. Note: In your
	 * intervalTree, this will get updated subsequently.
	 * 
	 * @param interval
	 *            the interval data member.
	 */
	public IntervalNode(IntervalADT<T> interval) {
		if (interval == null)
			throw new IllegalArgumentException();

		maxEnd = interval.getEnd();
		leftNode = null;
		rightNode = null;
		this.interval = interval;
	}

	/**
	 * Returns the next in-order successor of the BST. Hint: Return left-most
	 * node in the right subtree. Return null if there is no rightNode.
	 *
	 * @return in-order successor node
	 */
	public IntervalNode<T> getSuccessor() {
		if (rightNode == null)
			return null;

		IntervalNode<T> cur = rightNode;
		// go to the left most node
		while (cur.getLeftNode() != null)
			cur = cur.getLeftNode();
		return cur;
	}

	/**
	 * Returns the interval associated with the node.
	 * 
	 * @return the interval data field.
	 */
	public IntervalADT<T> getInterval() {
		return interval;
	}

	/**
	 * Setter for the interval.
	 * 
	 * @param interval
	 *            the interval to be set at this node.
	 */
	public void setInterval(IntervalADT<T> interval) {
		this.interval = interval;
	}

	/**
	 * Setter for the maxEnd. This represents the maximum end point associated
	 * in any interval stored at the subtree rooted at this node (inclusive of
	 * this node).
	 * 
	 * @param maxEnd
	 *            the maxEnd associated with this node.
	 *
	 */
	public void setMaxEnd(T maxEnd) {
		this.maxEnd = maxEnd;
	}

	/**
	 * Getter for the maxEnd member variable.
	 * 
	 * @return the maxEnd.
	 */
	public T getMaxEnd() {
		return maxEnd;
	}

	/**
	 * Getter for the leftNode reference.
	 *
	 * @return the reference of leftNode.
	 */
	public IntervalNode<T> getLeftNode() {
		return leftNode;
	}

	/**
	 * Setter for the leftNode of this node.
	 * 
	 * @param leftNode
	 *            the left node.
	 */
	public void setLeftNode(IntervalNode<T> leftNode) {
		this.leftNode = leftNode;
	}

	/**
	 * Getter for the rightNode of this node.
	 * 
	 * @return the rightNode.
	 */
	public IntervalNode<T> getRightNode() {
		return rightNode;
	}

	/**
	 * Setter for the rightNode of this node.
	 * 
	 * @param rightNode
	 *            the rightNode of this node.
	 */
	public void setRightNode(IntervalNode<T> rightNode) {
		this.rightNode = rightNode;
	}
}
