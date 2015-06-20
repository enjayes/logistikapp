/*
 * MemoryResidentMatrix.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.dtw;



/**
 * The Class MemoryResidentMatrix.
 */
class MemoryResidentMatrix implements CostMatrix {
	// CONSTANTS
	/** The Constant OUT_OF_WINDOW_VALUE. */
	private static final double OUT_OF_WINDOW_VALUE = Double.POSITIVE_INFINITY;

	// PRIVATE DATA
	/** The window. */
	private final SearchWindow window;
	
	/** The cell values. */
	private double[] cellValues;
	
	/** The col offsets. */
	private int[] colOffsets;

	// CONSTRUCTOR
	/**
	 * Instantiates a new memory resident matrix.
	 *
	 * @param searchWindow the search window
	 */
	MemoryResidentMatrix(SearchWindow searchWindow) {
		window = searchWindow;
		cellValues = new double[window.size()];
		colOffsets = new int[window.maxI() + 1];

		// Fill in the offset matrix
		int currentOffset = 0;
		for (int i = window.minI(); i <= window.maxI(); i++) {
			colOffsets[i] = currentOffset;
			currentOffset += window.maxJforI(i) - window.minJforI(i) + 1;
		}
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
		} else
			cellValues[colOffsets[col] + row - window.minJforI(col)] = value;
	}

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
		else
			return cellValues[colOffsets[col] + row - window.minJforI(col)];
	}

	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#size()
	 */
	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#size()
	 */
	@Override
	public int size() {
		return cellValues.length;
	}

} // end class MemoryResidentMatrix
