import java.util.ArrayList;
import java.util.Scanner;
public class get24 {
	private ArrayList<String> buf = new ArrayList<String>();
	private int count = 0;
	public static void main(String [] arg) {
		System.out.println("we are awesome");
		get24 a = new get24();
		a.find(new float[] {7,7,5,12});
	}
	
	public void find(float[] numbers) {
		// end of recursion
		if (numbers.length == 1) {
			if (numbers[0] == 24) {
				printBuf();
			}
		}
		// pick up two numbers
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers.length; j++) {
				// same number, skip
				if (i == j)
					continue;
				float[] tmp = new float[numbers.length - 1];
				// copy to tmp
				int p = 0;
				for (int k = 0; k < numbers.length; k++) {
					if (k != i && k != j)
						tmp[p++] = numbers[k];
				}
				
				tmp[tmp.length - 1] = numbers[i] + numbers[j];
				pushLine(numbers[i] + " + " + numbers[j] + " = " + (numbers[i]  + numbers[j]));
				find(tmp);
				popLine();
				
				tmp[tmp.length - 1] = numbers[i] - numbers[j];
				pushLine(numbers[i] + " - " + numbers[j] + " = " + (numbers[i]  - numbers[j]));
				find(tmp);
				popLine();
				
				tmp[tmp.length - 1] = numbers[i] * numbers[j];
				pushLine(numbers[i] + " * " + numbers[j] + " = " + (numbers[i]  * numbers[j]));
				find(tmp);
				popLine();
				
				tmp[tmp.length - 1] = numbers[i] / numbers[j];
				if ((int)tmp[tmp.length - 1] == tmp[tmp.length - 1]) {
				pushLine(numbers[i] + " / " + numbers[j] + " = " + (numbers[i]  / numbers[j]));
				find(tmp);
				popLine();
				}
			}
		}
	}
	
	private void pushLine(String str) {
		buf.add(str);
	}
	
	private void popLine() {
		buf.remove(buf.size() - 1);
	}
	
	private void printBuf() {
		System.out.println("found #" + ++count + ":");
		for (String line : buf)
			System.out.println(line);
		System.out.println();
	}
}
