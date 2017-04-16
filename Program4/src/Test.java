
public class Test {
	private static IntervalTreeADT<Integer> tree;
	private static int counter = 0;
	public static void main(String[] args) {
		tree = new IntervalTree<Integer>();
		
		add(0, 2);
		add(0, 30);
		add(0, 20);
		
		add(0, 40);
		add(0, 35);
		add(0, 25);
		add(0, 15);

		show();
		
		del(0, 40);
		show();
	}
	
	public static void add(int s, int e, String l) {
		IntervalADT<Integer> interval = new Interval<Integer>(s, e, l);
		tree.insert(interval);
	}
	
	public static void add(int s, int e) {
		IntervalADT<Integer> interval = new Interval<Integer>(s, e, "n" + counter++);
		tree.insert(interval);
	}
	
	public static void del(int s, int e) {
		IntervalADT<Integer> interval =
				new Interval<Integer>(s, e, "");
		try {
			tree.delete(interval);
		} catch (IntervalNotFoundException x) {
			x.printStackTrace();
		}
	}
	
	public static void show() {
		try {
			IntervalTreeGUI<Integer> window =
							new IntervalTreeGUI<Integer>(tree.getRoot());
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
