/*
 * ColMajorCell.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.matrix;



/**
 * The Class ColMajorCell.
 */
public class ColMajorCell {
	
	/** The col. */
	private final int col;
	
	/** The row. */
	private final int row;

	/**
	 * Gets the col.
	 *
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Gets the row.
	 *
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Instantiates a new col major cell.
	 *
	 * @param column the column
	 * @param row the row
	 */
	public ColMajorCell(int column, int row) {
		this.col = column;
		this.row = row;
	} // end Constructor

	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return (o instanceof ColMajorCell)
				&& (((ColMajorCell) o).col == this.col)
				&& (((ColMajorCell) o).row == this.row);
	}

	/* 
	 * @see java.lang.Object#hashCode()
	 */
	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (1 << col) + row;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + col + "," + row + ")";
	}

} // end class ColMajorCell
