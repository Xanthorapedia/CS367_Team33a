/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017
// PROJECT:          p3 Memory Efficient Merging of Sorted Files
// FILE:             Reducer.java
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

import java.io.*;
import java.util.*;

/**
 * Reducer solves the following problem: given a set of sorted input files (each
 * containing the same type of data), merge them into one sorted file.
 *
 */
public class Reducer {
	// list of files for stocking the PQ
	private List<FileIterator> fileList;
	private String type, dirName, outFile;

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java Reducer <weather|thesaurus> <dir_name> <output_file>");
			System.exit(1);
		}

		String type = args[0];
		String dirName = args[1];
		String outFile = args[2];

		Reducer r = new Reducer(type, dirName, outFile);
		r.run();

	}

	/**
	 * Constructs a new instance of Reducer with the given type (a string
	 * indicating which type of data is being merged), the directory which
	 * contains the files to be merged, and the name of the output file.
	 */
	public Reducer(String type, String dirName, String outFile) {
		this.type = type;
		this.dirName = dirName;
		this.outFile = outFile;
	}

	/**
	 * Carries out the file merging algorithm described in the assignment
	 * description.
	 */
	public void run() {
		File dir = new File(dirName);
		File[] files = dir.listFiles();
		Arrays.sort(files);

		Record r = null;

		// list of files for stocking the PQ
		fileList = new ArrayList<FileIterator>();

		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isFile() && f.getName().endsWith(".txt")) {
				fileList.add(new FileIterator(f.getAbsolutePath(), i));
			}
		}

		switch (type) {
		case "weather":
			r = new WeatherRecord(fileList.size());
			break;
		case "thesaurus":
			r = new ThesaurusRecord(fileList.size());
			break;
		default:
			System.out.println("Invalid type of data! " + type);
			System.exit(1);
		}

		FileLinePriorityQueue q = new FileLinePriorityQueue(fileList.size(), r.getComparator());
		try {
			// first-round fill
			for (int i = 0; i < fileList.size(); i++)
				q.insert(fileList.get(i).next());
			FileWriter w = new FileWriter(outFile);
			
			// the previous key
			String prevKey = "";
			while (!q.isEmpty()) {
				FileLine tmp = q.removeMin();
				String thisKey = getKey(tmp.getString());
				// if is a different key but not the first time
				if (!thisKey.equals(prevKey) && !prevKey.equals("")) {
					//write the what in record to outFile, clear and update key
					w.write(r.toString());
					r.clear();
				}
				prevKey = thisKey;
				r.join(tmp);
				
				if (tmp.getFileIterator().hasNext())
					q.insert(tmp.getFileIterator().next());
			}
			// special treatment for the last record
			w.write(r.toString());
			r.clear();
			
			w.close();
		} catch (PriorityQueueEmptyException e) {
			System.out.println("PriorityQueueEmptyException");
		} catch (PriorityQueueFullException e) {
			System.out.println("PriorityQueueFullException");
		} catch (IOException e){
			System.out.println("IOException");
		}

	}
	
	/**
	 * Gets the key of the String record. Before the last ',' for whether data
	 * or before the last ':' for thesaurus data
	 * @param str the record String
	 * @return the key of the record
	 */
	private String getKey(String str) {
		if (type.equals("weather"))
			return str.substring(0, str.lastIndexOf(','));
		else
			return str.substring(0, str.lastIndexOf(':'));
	}
}
