
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The WeatherRecord class is the child class of Record to be used when merging weather data. Station and Date
 * store the station and date associated with each weather reading that this object stores.
 * l stores the weather readings, in the same order as the files from which they came are indexed.
 */
public class WeatherRecord extends Record{

	ArrayList<Double> list;
	String key;

	/**
	 * Constructs a new WeatherRecord by passing the parameter to the parent constructor
	 * and then calling the clear method()
	 */
	public WeatherRecord(int numFiles) {
		super(numFiles);
		clear();
	}

	/**
	 * This comparator should first compare the stations associated with the given FileLines. If 
	 * they are the same, then the dates should be compared. 
	 */
	private class WeatherLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			String[] data = l1.getString().split(",");
			String key1 = data[0] + "," + data[1];
			data = l2.getString().split(",");
			String key2 = data[0] + "," + data[1];
			return key1.compareTo(key2);
		}

		public boolean equals(Object o) {
			return this.equals(o);
		}
	}

	/**
	 * This method should simply create and return a new instance of the WeatherLineComparator
	 * class.
	 */
	public Comparator<FileLine> getComparator() {
		return new WeatherLineComparator();
	}

	/**
	 * This method should fill each entry in the data structure containing
	 * the readings with Double.MIN_VALUEsp
	 */
	public void clear() {
		list = new ArrayList<Double>();
		for(int i =0; i < this.getNumFiles(); i++)
			list.add(Double.MIN_VALUE);
		key = "";
	}

	/**
	 * This method should parse the String associated with the given FileLine to get the station, date, and reading
	 * contained therein. Then, in the data structure holding each reading, the entry with index equal to the parameter 
	 * FileLine's index should be set to the value of the reading. Also, so that
	 * this method will handle merging when this WeatherRecord is empty, the station and date associated with this
	 * WeatherRecord should be set to the station and date values which were similarly parsed.
	 */
	public void join(FileLine li) {
		String[] data = li.getString().split(",");
		key = data[0] + "," + data[1];
		int index = li.getFileIterator().getIndex();
		
		while(list.size() <= index)
			list.add(Double.MIN_VALUE);
		
		list.set(index, Double.parseDouble(data[2]));
	}

	/**
	 * See the assignment description and example runs for the exact output format.
	 */
	public String toString() {
		String str = key;
		for (int i = 0; i < list.size(); i++)
			str += "," + (list.get(i) == Double.MIN_VALUE ? "-" : list.get(i));
		str += "\n";
		return str;
	}
}
