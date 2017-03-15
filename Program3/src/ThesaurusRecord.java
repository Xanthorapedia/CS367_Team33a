import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The ThesaurusRecord class is the child class of Record to be used when
 * merging thesaurus data.
 */

public class ThesaurusRecord extends Record {
	private ArrayList<String> list;
	private String word;

	/**
	 * Constructs a new ThesaurusRecord by passing the parameter to the parent
	 * constructor and then calling the clear method()
	 */
	public ThesaurusRecord(int numFiles) {
		super(numFiles);
		clear();
	}

	/**
	 * This Comparator should simply behave like the default (lexicographic)
	 * compareTo() method for Strings, applied to the portions of the FileLines'
	 * Strings up to the ":" The getComparator() method of the ThesaurusRecord
	 * class will simply return an instance of this class.
	 */
	private class ThesaurusLineComparator implements Comparator<FileLine> {
		public int compare(FileLine l1, FileLine l2) {
			String str1 = l1.getString();
			str1 = str1.substring(0, str1.indexOf(':'));
			String str2 = l2.getString();
			str2 = str2.substring(0, str2.indexOf(':'));
			return str1.compareTo(str2);
		}

		public boolean equals(Object o) {
			return this.equals(o);
		}
	}

	/**
	 * This method should simply create and return a new instance of the
	 * ThesaurusLineComparator class.
	 */
	public Comparator<FileLine> getComparator() {
		return new ThesaurusLineComparator();
	}

	/**
	 * This method should (1) set the word to null and (2) empty the list of
	 * synonyms.
	 */
	public void clear() {
		list = new ArrayList<String>();
		word = "";
	}

	/**
	 * This method should parse the list of synonyms contained in the given
	 * FileLine and insert any which are not already found in this
	 * ThesaurusRecord's list of synonyms.
	 */
	public void join(FileLine w) {
		String str = w.getString();
		int colPos = str.indexOf(':');

		word = str.substring(0, colPos);
		str = str.substring(colPos);

		// add new Strings
		String[] data = str.split(",");
		for (String tmp : data)
			if (!list.contains(tmp))
				list.add(tmp);

		// sort the list in order
		Collections.sort(list);

	}

	/**
	 * See the assignment description and example runs for the exact output
	 * format.
	 */
	public String toString() {
		String str = word + ":";
		for (int i = 0; i < list.size() - 1; i++)
			str += list.get(i) + ",";
		if (list.size() != 0)
			str += list.get(list.size() - 1);
		return str;
	}
}
