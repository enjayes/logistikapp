/*
 * SwapFileMatrix.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.dtw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

import com.tum.ident.fastdtw.lang.TypeConversions;



/**
 * The Class SwapFileMatrix.
 */
class SwapFileMatrix implements CostMatrix {
	// CONSTANTS
	/** The Constant OUT_OF_WINDOW_VALUE. */
	private static final double OUT_OF_WINDOW_VALUE = Double.POSITIVE_INFINITY;
	
	/** The Constant RAND_GEN. */
	private static final Random RAND_GEN = new Random();

	// PRIVATE DATA
	/** The window. */
	private final SearchWindow window;

	// Private data needed to store the last 2 colums of the matrix.
	/** The last col. */
	private double[] lastCol;
	
	/** The curr col. */
	private double[] currCol;
	
	/** The curr col index. */
	private int currColIndex;
	
	/** The min last row. */
	private int minLastRow;
	
	/** The min curr row. */
	private int minCurrRow;

	// Private data needed to read values from the swap file.
	/** The swap file. */
	private final File swapFile;
	
	/** The cell values file. */
	private final RandomAccessFile cellValuesFile;
	
	/** The is swap file freed. */
	private boolean isSwapFileFreed;
	
	/** The col offsets. */
	private final long[] colOffsets;

	// CONSTRUCTOR
	/**
	 * Instantiates a new swap file matrix.
	 *
	 * @param searchWindow the search window
	 */
	SwapFileMatrix(SearchWindow searchWindow) {
		window = searchWindow;

		if (window.maxI() > 0) {
			currCol = new double[window.maxJforI(1) - window.minJforI(1) + 1];
			currColIndex = 1;
			minLastRow = window.minJforI(currColIndex - 1);
		} else
			// special case for a <=1 point time series, less than 2 columns to
			// fill in
			currColIndex = 0;

		minCurrRow = window.minJforI(currColIndex);
		lastCol = new double[window.maxJforI(0) - window.minJforI(0) + 1];

		swapFile = new File("swap" + RAND_GEN.nextLong());
		isSwapFileFreed = false;
		// swapFile.deleteOnExit();

		colOffsets = new long[window.maxI() + 1];

		try {
			cellValuesFile = new RandomAccessFile(swapFile, "rw");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("ERROR:  Unable to create swap file: "
					+ swapFile);
		} // end try
	} // end Constructor

	// PUBLIC FUNCTIONS
	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#put(int, int, double)
	 */
	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#put(int, int, double)
	 */
	@Override
	public void put(int col, int row, double value) {
		if ((row < window.minJforI(col)) || (row > window.maxJforI(col))) {
			throw new RuntimeException("CostMatrix is filled in a cell (col="
					+ col + ", row=" + row + ") that is not in the "
					+ "search window");
		} else {
			if (col == currColIndex)
				currCol[row - minCurrRow] = value;
			else if (col == currColIndex - 1) {
				lastCol[row - minLastRow] = value;
			} else if (col == currColIndex + 1) {
				// Write the last column to the swap file.
				try {
					if (isSwapFileFreed)
						throw new RuntimeException(
								"The SwapFileMatrix has been freeded by the freeMem() method");
					else {
						cellValuesFile.seek(cellValuesFile.length()); // move
																		// file
																		// poiter
																		// to
																		// end
																		// of
																		// file
						colOffsets[currColIndex - 1] = cellValuesFile
								.getFilePointer();

						// Write an entire column to the swap file.
						cellValuesFile.write(TypeConversions
								.doubleArrayToByteArray(lastCol));
					} // end if
				} catch (IOException e) {
					throw new RuntimeException(
							"Unable to fill the CostMatrix in the Swap file (IOException)");
				} // end try

				lastCol = currCol;
				minLastRow = minCurrRow;
				minCurrRow = window.minJforI(col);
				currColIndex++;
				currCol = new double[window.maxJforI(col)
						- window.minJforI(col) + 1];
				currCol[row - minCurrRow] = value;
			} else
				throw new RuntimeException(
						"A SwapFileMatrix can only fill in 2 adjacentcolumns at a time");
		} // end if
	} // end put(...)

	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#get(int, int)
	 */
	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#get(int, int)
	 */
	@Override
	public double get(int col, int row) {
		if ((row < window.minJforI(col)) || (row > window.maxJforI(col)))
			return OUT_OF_WINDOW_VALUE;
		else if (col == currColIndex)
			return currCol[row - minCurrRow];
		else if (col == currColIndex - 1)
			return lastCol[row - minLastRow];
		else {
			try {
				if (isSwapFileFreed)
					throw new RuntimeException(
							"The SwapFileMatrix has been freeded by the freeMem() method");
				else {
					cellValuesFile.seek(colOffsets[col] + 8
							* (row - window.minJforI(col)));
					return cellValuesFile.readDouble();
				} // end if
			} catch (IOException e) {
				if (col > currColIndex)
					throw new RuntimeException(
							"The requested value is in the search window but has not been entered into "
									+ "the matrix: (col=" + col + "row=" + row
									+ ").");
				else
					throw new RuntimeException(
							"Unable to read CostMatrix in the Swap file (IOException)");
			} // end try
		} // end if
	} // end get(..)

	// This method closes and delets the swap file when the object's finalize()
	// mehtod is called. This method will
	// ONLY be called by the JVM if the object is garbage collected while the
	// application is still running.
	// This method must be called explicitly to guarantee that the swap file is
	// deleted.
	/* 
	 * @see java.lang.Object#finalize()
	 */
	/* 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		// Close and Delete the (possibly VERY large) swap file.
		try {
			if (!isSwapFileFreed)
				cellValuesFile.close();
		} catch (Exception e) {
			System.err.println("unable to close swap file '"
					+ this.swapFile.getPath() + "' during finialization");
		} finally {
			swapFile.delete(); // delete the swap file
			super.finalize(); // ensure that finalization continues for parent
								// if an exception is thrown
		} // end try
	} // end finalize()

	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#size()
	 */
	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#size()
	 */
	@Override
	public int size() {
		return window.size();
	}

	/**
	 * Free mem.
	 */
	public void freeMem() {
		try {
			cellValuesFile.close();
		} catch (IOException e) {
			System.err.println("unable to close swap file '"
					+ this.swapFile.getPath() + "'");
		} finally {
			if (!swapFile.delete())
				System.err.println("unable to delete swap file '"
						+ this.swapFile.getPath() + "'");
		} // end try
	} // end freeMem

} // end class SwapFileMatrix

