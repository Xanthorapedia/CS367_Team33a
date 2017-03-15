
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The WeatherRecord class is the child class of Record to be used when merging weather data. Station and Date
 * store the station and date associated with each weather reading that this object stores.
 * l stores the weather readings, in the same order as the files from which they came are indexed.
 */
public class WeatherRecord extends Record{

	ArrayList<String> list;

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
			int compare = 0;
			//compare the stations
			String str1 = l1.getString();
			str1 = str1.substring(0, str1.indexOf(','));
			String str2 = l2.getString();
			str2 = str2.substring(0, str2.indexOf(','));

			//if the stations are the same, compare the dates
			if(str1.equals(str2)){
				String[] date1 = l1.getString().split(",");
				String[] date2 = l2.getString().split(",");
				String d1 = date1[1];
				String d2 = date2[1];
				compare = d1.compareTo(d2);

			}
			return compare;

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
		list = new ArrayList<String>();
		for(int i =0; i < list.size(); i++){
			list.add(Double.toString(Double.MIN_VALUE));
		}
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
		for(int j = 0; j < data.length; j++){
			//if the index is an empty, replace with "-"
			if(data[j] == "")
			data[j] = "-";
			list.add(data[j]);
		}
		Collections.sort(list); //sort the list in order


	}

	/**
	 * See the assignment description and example runs for the exact output format.
	 */
	public String toString() {
		String str = "";
		for (int i = 0; i < list.size() - 1; i++)
			str += list.get(i) + ",";
		return str;
	}
}
