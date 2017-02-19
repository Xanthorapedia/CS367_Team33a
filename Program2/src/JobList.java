import java.util.Iterator;

public class JobList implements ListADT<Job> {

	private Listnode<Job> head; 
	private int numItems;

	public JobList(){
		head = new Listnode<Job>(null);
		numItems =0; 

	}

	@Override
	public Iterator<Job> iterator() {
		return new JobListIterator(head);
	}

	@Override
	public void add(Job item) {
		if(item == null)
			throw new IllegalArgumentException();
		Listnode<Job> newnode = new Listnode<Job>(item);
		Listnode<Job> node = head;
		while(node.getNext() != null)	
			node = node.getNext();
		node.setNext(newnode);

		numItems++;

	}

	@Override
	public void add(int pos, Job item) {

		if(item == null){
			throw new IllegalArgumentException();
		}

		if(pos < 0 || pos >= numItems){
			throw new IndexOutOfBoundsException();
		}

		Listnode<Job> newNode = new Listnode<Job>(item);

		Listnode<Job> cur = head;
		for(int curr = 0; curr < pos; curr++){
			cur = cur.getNext();
		}
		newNode.setNext(cur.getNext());
		cur.setNext(newNode);



		numItems++;



	}

	@Override
	public boolean contains(Job item) {

		if(item == null){
			throw new IllegalArgumentException();
		}
		for(Job e: this){
			if(item.equals(e)){
				return true;
			}
		}
		return false;

	}

	@Override
	public Job get(int pos) {
		if(pos < 0 || pos >= numItems){
			throw new IndexOutOfBoundsException();
		}
		Listnode<Job> curr = head.getNext();
		for(int i = 0; i < pos; i++){
			curr = curr.getNext();

		}



		return curr.getData();
	}

	@Override
	public boolean isEmpty() {
		return numItems == 0; //return true if the list is empty 
	}

	@Override
	public Job remove(int pos) {
		if(pos < 0 || pos >= numItems){
			throw new IndexOutOfBoundsException();
		}


		Listnode<Job> curr = new Listnode<Job>(null);
		curr = head;
		for(int i = 0; i < pos; i++){
			curr = curr.getNext(); 
		}
		Job item = curr.getNext().getData();
		curr.setNext(curr.getNext().getNext());


		return item;
	}




	@Override
	public int size() {

		return numItems;
	}

}
