/*
 * WindowMatrix.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.dtw;



/**
 * The Class WindowMatrix.
 */
class WindowMatrix implements CostMatrix {
	// PRIVATE DATA
	/** The window cells. */
	private CostMatrix windowCells;

	// CONSTRUCTOR
	/**
	 * Instantiates a new window matrix.
	 *
	 * @param searchWindow the search window
	 */
	WindowMatrix(SearchWindow searchWindow) {
		try {
			windowCells = new MemoryResidentMatrix(searchWindow);
		} catch (OutOfMemoryError e) {
			System.err
					.println("Ran out of memory initializing window matrix, all cells in the window cannot fit into "
							+ "main memory.  Will use a swap file instead (will run ~50% slower)");
			System.gc();
			windowCells = new SwapFileMatrix(searchWindow);
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
		windowCells.put(col, row, value);
	}

	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#get(int, int)
	 */
	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#get(int, int)
	 */
	@Override
	public double get(int col, int row) {
		return windowCells.get(col, row);
	}

	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#size()
	 */
	/* 
	 * @see com.tum.ident.fastdtw.dtw.CostMatrix#size()
	 */
	@Override
	public int size() {
		return windowCells.size();
	}

	/**
	 * Free mem.
	 */
	public void freeMem() {
		// Resources only need to be freed for a SwapFileMatrix.
		if (windowCells instanceof SwapFileMatrix) {
			try {
				((SwapFileMatrix) windowCells).freeMem();
			} catch (Throwable t) {
				// ignore the exception
			} // end try
		} // end if
	} // end freeMem()

} // end WindowMatrix
