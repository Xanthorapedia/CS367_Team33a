
public class Test {

	public static void main(String[] args) {
		System.out.println(euclid(7, 4));
	}
	
	public static int euclid(int x, int y) {
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
