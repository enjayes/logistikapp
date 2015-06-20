/*
 * CostMatrix.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.dtw;



/**
 * The Interface CostMatrix.
 */
interface CostMatrix {
	
	/**
	 * Put.
	 *
	 * @param col the col
	 * @param row the row
	 * @param value the value
	 */
	public void put(int col, int row, double value);

	/**
	 * Gets the.
	 *
	 * @param col the col
	 * @param row the row
	 * @return the double
	 */
	public double get(int col, int row);

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size();

} // end interface CostMatrix
