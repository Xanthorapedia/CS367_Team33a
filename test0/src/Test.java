
public class Test {

	public static void main(String[] args) {
		System.out.println(euclid4(3, 1));
	}
	
	public static int euclid4(int x, int y) {
		if (x < y) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		// x >= y
		
		while(y > 0) {
			int tmp = x % y;
			x = y;
			y = tmp;
		}
		return x;
	}
}
