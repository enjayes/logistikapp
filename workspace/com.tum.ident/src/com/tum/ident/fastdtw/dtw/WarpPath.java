/*
 * WarpPath.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.dtw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import com.tum.ident.fastdtw.matrix.ColMajorCell;



/**
 * The Class WarpPath.
 */
public class WarpPath {
	// DATA
	/** The ts iindexes. */
	private final ArrayList<Object> tsIindexes; // ArrayList of Integer
	
	/** The ts jindexes. */
	private final ArrayList<Object> tsJindexes; // ArrayList of Integer

	// CONSTRUCTORS
	/**
	 * Instantiates a new warp path.
	 */
	public WarpPath() {
		tsIindexes = new ArrayList<Object>();
		tsJindexes = new ArrayList<Object>();
	}

	/**
	 * Instantiates a new warp path.
	 *
	 * @param initialCapacity the initial capacity
	 */
	public WarpPath(int initialCapacity) {
		this();
		tsIindexes.ensureCapacity(initialCapacity);
		tsJindexes.ensureCapacity(initialCapacity);

	}

	/**
	 * Instantiates a new warp path.
	 *
	 * @param inputFile the input file
	 */
	public WarpPath(String inputFile) {
		this();

		try {
			// Record the Label names (fropm the top row.of the input file).
			final BufferedReader br = new BufferedReader(new FileReader(
					inputFile)); // open the input file

			// Read Cluster assignments.
			String line;
			while ((line = br.readLine()) != null) // read lines until end of
													// file
			{
				final StringTokenizer st = new StringTokenizer(line, ",", false);
				if (st.countTokens() == 2) {
					tsIindexes.add(new Integer(st.nextToken()));
					tsJindexes.add(new Integer(st.nextToken()));
				} else {
					br.close();
					throw new RuntimeException("The Warp Path File '"
							+ inputFile
							+ "' has an incorrect format.  There must be\n"
							+ "two numbers per line separated by commas");
				}
			} // end while loop
			br.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException("ERROR:  The file '" + inputFile
					+ "' was not found.");
		} catch (IOException e) {
			throw new RuntimeException("ERROR:  Problem reading the file '"
					+ inputFile + "'.");
		} // end try block

	}

	// FUNCTIONS
	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return tsIindexes.size();
	}

	/**
	 * Min i.
	 *
	 * @return the int
	 */
	public int minI() {
		return ((Integer) tsIindexes.get(0)).intValue();
	}

	/**
	 * Min j.
	 *
	 * @return the int
	 */
	public int minJ() {
		return ((Integer) tsJindexes.get(0)).intValue();
	}

	/**
	 * Max i.
	 *
	 * @return the int
	 */
	public int maxI() {
		return ((Integer) tsIindexes.get(tsIindexes.size() - 1)).intValue();
	}

	/**
	 * Max j.
	 *
	 * @return the int
	 */
	public int maxJ() {
		return ((Integer) tsJindexes.get(tsJindexes.size() - 1)).intValue();
	}

	/**
	 * Adds the first.
	 *
	 * @param i the i
	 * @param j the j
	 */
	public void addFirst(int i, int j) {
		tsIindexes.add(0, new Integer(i));
		tsJindexes.add(0, new Integer(j));
	}

	/**
	 * Adds the last.
	 *
	 * @param i the i
	 * @param j the j
	 */
	public void addLast(int i, int j) {
		tsIindexes.add(new Integer(i));
		tsJindexes.add(new Integer(j));
	}

	/**
	 * Gets the matching indexes for i.
	 *
	 * @param i the i
	 * @return the matching indexes for i
	 */
	public ArrayList<Object> getMatchingIndexesForI(int i) {
		int index = tsIindexes.indexOf(new Integer(i));
		if (index < 0)
			throw new RuntimeException("ERROR:  index '" + i
					+ " is not in the " + "warp path.");
		final ArrayList<Object> matchingJs = new ArrayList<Object>();
		while (index < tsIindexes.size()
				&& tsIindexes.get(index).equals(new Integer(i)))
			matchingJs.add(tsJindexes.get(index++));

		return matchingJs;
	} // end getMatchingIndexesForI(int i)

	/**
	 * Gets the matching indexes for j.
	 *
	 * @param j the j
	 * @return the matching indexes for j
	 */
	public ArrayList<Object> getMatchingIndexesForJ(int j) {
		int index = tsJindexes.indexOf(new Integer(j));
		if (index < 0)
			throw new RuntimeException("ERROR:  index '" + j
					+ " is not in the " + "warp path.");
		final ArrayList<Object> matchingIs = new ArrayList<Object>();
		while (index < tsJindexes.size()
				&& tsJindexes.get(index).equals(new Integer(j)))
			matchingIs.add(tsIindexes.get(index++));

		return matchingIs;
	} // end getMatchingIndexesForI(int i)

	// Create a new WarpPath that is the same as THIS WarpPath, but J is the
	// reference template, rather than I.
	/**
	 * Inverted copy.
	 *
	 * @return the warp path
	 */
	public WarpPath invertedCopy() {
		final WarpPath newWarpPath = new WarpPath();
		for (int x = 0; x < tsIindexes.size(); x++)
			newWarpPath.addLast(((Integer) tsJindexes.get(x)).intValue(),
					((Integer) tsIindexes.get(x)).intValue());

		return newWarpPath;
	}

	// Swap I and J so that the warp path now indicates that J is the template
	// rather than I.
	/**
	 * Invert.
	 */
	public void invert() {
		for (int x = 0; x < tsIindexes.size(); x++) {
			final Object temp = tsIindexes.get(x);
			tsIindexes.set(x, tsJindexes.get(x));
			tsJindexes.set(x, temp);
		}
	} // end invert()

	/**
	 * Gets the.
	 *
	 * @param index the index
	 * @return the col major cell
	 */
	public ColMajorCell get(int index) {
		if ((index > this.size()) || (index < 0))
			throw new NoSuchElementException();
		else
			return new ColMajorCell(
					((Integer) tsIindexes.get(index)).intValue(),
					((Integer) tsJindexes.get(index)).intValue());
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer outStr = new StringBuffer("[");
		for (int x = 0; x < tsIindexes.size(); x++) {
			outStr.append("(" + tsIindexes.get(x) + "," + tsJindexes.get(x)
					+ ")");
			if (x < tsIindexes.size() - 1)
				outStr.append(",");
		} // end for loop

		return new String(outStr.append("]"));
	} // end toString()

	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof WarpPath)) // trivial false test
		{
			final WarpPath p = (WarpPath) obj;
			if ((p.size() == this.size()) && (p.maxI() == this.maxI())
					&& (p.maxJ() == this.maxJ())) // less trivial reject
			{
				// Compare each value in the warp path for equality
				for (int x = 0; x < this.size(); x++)
					if (!(this.get(x).equals(p.get(x))))
						return false;

				return true;
			} else
				return false;
		} else
			return false;
	} // end equals

	/* 
	 * @see java.lang.Object#hashCode()
	 */
	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return tsIindexes.hashCode() * tsJindexes.hashCode();
	}

} // end class WarpPath
