/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p3 Memory Efficient Merging of Sorted Files
// FILE:             ThesaurusRecord.java
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The ThesaurusRecord class is the child class of Record to be used when
 * merging thesaurus data.
 */
public class ThesaurusRecord extends Record {
	
	/** the thesaurus record */
	private ArrayList<String> list;
	/** the key (line header) of the record */
	private String key;

	/**
	 * Constructs a new ThesaurusRecord by passing the parameter to the parent
	 * constructor and then calling the clear method()
	 * 
	 * @param numFiles the number of files to be merged
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
			// gets the key of each of the FileLines
			String key1 = l1.getString();
			key1 = key1.substring(0, key1.indexOf(':'));
			String key2 = l2.getString();
			key2 = key2.substring(0, key2.indexOf(':'));
			return key1.compareTo(key2);
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
		key = "";
	}

	/**
	 * This method should parse the list of synonyms contained in the given
	 * FileLine and insert any which are not already found in this
	 * ThesaurusRecord's list of synonyms.
	 * 
	 * @param w a line of a input file
	 */
	public void join(FileLine w) {
		String str = w.getString();
		int colPos = str.indexOf(':');

		// breaks the string at ':', parse the rest
		key = str.substring(0, colPos);
		str = str.substring(colPos + 1);

		// only add new Strings
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
		String str = key + ":";
		
		// prints except for the last item with ","
		for (int i = 0; i < list.size() - 1; i++)
			str += list.get(i) + ",";
		if (list.size() != 0)
			str += list.get(list.size() - 1);
		str += "\n";
		return str;
	}
}
