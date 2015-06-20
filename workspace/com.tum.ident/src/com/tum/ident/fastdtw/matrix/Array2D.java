/*
 * Array2D.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.matrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;



/**
 * The Class Array2D.
 */
public class Array2D {
	// PRIVATE DATA
	/** The rows. */
	final private ArrayList<ArrayList<Object>> rows; // ArrayList of ArrayList
														// (an array of rows in
														// the array)
	/** The num of elements. */
														private int numOfElements;

	// CONSTRUCTOR
	/**
	 * Instantiates a new array2 d.
	 */
	public Array2D() {
		rows = new ArrayList<ArrayList<Object>>();
		numOfElements = 0;
	}

	/**
	 * Instantiates a new array2 d.
	 *
	 * @param array the array
	 */
	public Array2D(Array2D array) {
		this.rows = new ArrayList<ArrayList<Object>>(array.rows);
		this.numOfElements = array.numOfElements;
	}

	// PUBLIC FU?NCTIONS
	/**
	 * Clear.
	 */
	public void clear() {
		rows.clear();
		numOfElements = 0;
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return numOfElements;
	}

	/**
	 * Num of rows.
	 *
	 * @return the int
	 */
	public int numOfRows() {
		return rows.size();
	}

	/**
	 * Gets the size of row.
	 *
	 * @param row the row
	 * @return the size of row
	 */
	public int getSizeOfRow(int row) {
		return rows.get(row).size();
	}

	/**
	 * Gets the.
	 *
	 * @param row the row
	 * @param col the col
	 * @return the object
	 */
	public Object get(int row, int col) {
		return rows.get(row).get(col);
	}

	/**
	 * Sets the.
	 *
	 * @param row the row
	 * @param col the col
	 * @param newVal the new val
	 */
	public void set(int row, int col, Object newVal) {
		rows.get(row).set(col, newVal);
	}

	/**
	 * Adds the to end of row.
	 *
	 * @param row the row
	 * @param value the value
	 */
	public void addToEndOfRow(int row, Object value) {
		rows.get(row).add(value);
		numOfElements++;
	}

	/**
	 * Adds the all to end of row.
	 *
	 * @param row the row
	 * @param objects the objects
	 */
	public void addAllToEndOfRow(int row, Collection<?> objects) {
		final Iterator<?> i = objects.iterator();
		while (i.hasNext()) {
			rows.get(row).add(i.next());
			numOfElements++;
		}
	}

	/**
	 * Adds the to new first row.
	 *
	 * @param value the value
	 */
	public void addToNewFirstRow(Object value) {
		final ArrayList<Object> newFirstRow = new ArrayList<Object>(1);
		newFirstRow.add(value);
		rows.add(0, newFirstRow);
		numOfElements++;
	}

	/**
	 * Adds the to new last row.
	 *
	 * @param value the value
	 */
	public void addToNewLastRow(Object value) {
		final ArrayList<Object> newLastRow = new ArrayList<Object>(1);
		newLastRow.add(value);
		rows.add(newLastRow);
		numOfElements++;
	}

	/**
	 * Adds the all to new last row.
	 *
	 * @param objects the objects
	 */
	public void addAllToNewLastRow(Collection<?> objects) {
		final Iterator<?> i = objects.iterator();
		final ArrayList<Object> newLastRow = new ArrayList<Object>(1);
		while (i.hasNext()) {
			newLastRow.add(i.next());
			numOfElements++;
		}

		rows.add(newLastRow);
	}

	/**
	 * Removes the first row.
	 */
	public void removeFirstRow() {
		numOfElements -= rows.get(0).size();
		rows.remove(0);
	}

	/**
	 * Removes the last row.
	 */
	public void removeLastRow() {
		numOfElements -= rows.get(rows.size() - 1).size();
		rows.remove(rows.size() - 1);
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String outStr = "";
		for (int r = 0; r < rows.size(); r++) {
			final ArrayList<?> currentRow = rows.get(r);
			for (int c = 0; c < currentRow.size(); c++) {
				outStr += currentRow.get(c);
				if (c == currentRow.size() - 1)
					outStr += "\n";
				else
					outStr += ",";
			}
		} // end for

		return outStr;
	}

} // end class matrix.Array2D
