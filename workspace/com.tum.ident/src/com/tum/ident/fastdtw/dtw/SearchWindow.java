/*
 * SearchWindow.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.dtw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.tum.ident.fastdtw.matrix.ColMajorCell;



/**
 * The Class SearchWindow.
 */
abstract public class SearchWindow {
	// PRIVATE DATA
	/** The min values. */
	private final int[] minValues;
	
	/** The max values. */
	private final int[] maxValues;
	
	/** The max j. */
	private final int maxJ;
	
	/** The size. */
	private int size;
	
	/** The mod count. */
	private int modCount;

	// CONSTRUCTOR
	/**
	 * Instantiates a new search window.
	 *
	 * @param tsIsize the ts isize
	 * @param tsJsize the ts jsize
	 */
	public SearchWindow(int tsIsize, int tsJsize) {
		minValues = new int[tsIsize];
		maxValues = new int[tsIsize];
		Arrays.fill(minValues, -1);
		maxJ = tsJsize - 1;
		size = 0;
		modCount = 0;
	}

	// PUBLIC FUNCTIONS
	/**
	 * Checks if is in window.
	 *
	 * @param i the i
	 * @param j the j
	 * @return true, if is in window
	 */
	public final boolean isInWindow(int i, int j) {
		return (i >= minI()) && (i <= maxI()) && (minValues[i] <= j)
				&& (maxValues[i] >= j);
	}

	/**
	 * Min i.
	 *
	 * @return the int
	 */
	public final int minI() {
		return 0;
	}

	/**
	 * Max i.
	 *
	 * @return the int
	 */
	public final int maxI() {
		return minValues.length - 1;
	}

	/**
	 * Min j.
	 *
	 * @return the int
	 */
	public final int minJ() {
		return 0;
	}

	/**
	 * Max j.
	 *
	 * @return the int
	 */
	public final int maxJ() {
		return maxJ;
	}

	/**
	 * Min jfor i.
	 *
	 * @param i the i
	 * @return the int
	 */
	public final int minJforI(int i) {
		return minValues[i];
	}

	/**
	 * Max jfor i.
	 *
	 * @param i the i
	 * @return the int
	 */
	public final int maxJforI(int i) {
		return maxValues[i];
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public final int size() {
		return size;
	}

	// Iterates through all cells in the search window in the order that Dynamic
	// Time Warping needs to evaluate them. (first to last column (0..maxI),
	// bottom up (o..maxJ))
	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	public final Iterator<?> iterator() {
		return new SearchWindowIterator(this);
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		final StringBuffer outStr = new StringBuffer();

		for (int i = minI(); i <= maxI(); i++) {
			outStr.append("i=" + i + ", j=" + minValues[i] + "..."
					+ maxValues[i]);
			if (i != maxI())
				outStr.append("\n");
		} // end for loop

		return outStr.toString();
	} // end toString()

	/**
	 * Gets the mod count.
	 *
	 * @return the mod count
	 */
	protected int getModCount() {
		return modCount;
	}

	// PROTECTED FUNCTIONS
	// Expands the current window by a s pecified radius.
	/**
	 * Expand window.
	 *
	 * @param radius the radius
	 */
	protected final void expandWindow(int radius) {
		if (radius > 0) {
			// Expand the search window by one before expanding by the remainder
			// of the radius because the function
			// "expandSearchWindow(.) may not work correctly if the path has a
			// width of only 1.
			expandSearchWindow(1);
			expandSearchWindow(radius - 1);
		}
	}

	/**
	 * Expand search window.
	 *
	 * @param radius the radius
	 */
	private final void expandSearchWindow(int radius) {
		if (radius > 0) // if radius <=0 then no search is necessary, use the
						// current search window
		{
			// Add all cells in the current Window to an array, iterating
			// through the window and expanding the window
			// at the same time is not possible because the window can't be
			// changed during iteration through the cells.
			final ArrayList<Object> windowCells = new ArrayList<Object>(
					this.size());
			for (final Iterator<?> cellIter = this.iterator(); cellIter
					.hasNext();)
				windowCells.add(cellIter.next());

			for (int cell = 0; cell < windowCells.size(); cell++) {
				final ColMajorCell currentCell = (ColMajorCell) windowCells
						.get(cell);

				if ((currentCell.getCol() != minI())
						&& (currentCell.getRow() != maxJ()))// move to upper
															// left if possible
				{
					// Either extend full search radius or some fraction until
					// edges of matrix are met.
					final int targetCol = currentCell.getCol() - radius;
					final int targetRow = currentCell.getRow() + radius;

					if ((targetCol >= minI()) && (targetRow <= maxJ()))
						markVisited(targetCol, targetRow);
					else {
						// Expand the window only to the edge of the matrix.
						final int cellsPastEdge = Math.max(minI() - targetCol,
								targetRow - maxJ());
						markVisited(targetCol + cellsPastEdge, targetRow
								- cellsPastEdge);
					} // end if
				} // end if

				if (currentCell.getRow() != maxJ()) // move up if possible
				{
					// Either extend full search radius or some fraction until
					// edges of matrix are met.
					final int targetCol = currentCell.getCol();
					final int targetRow = currentCell.getRow() + radius;

					if (targetRow <= maxJ())
						markVisited(targetCol, targetRow); // radius does not go
															// past the edges of
															// the matrix
					else {
						// Expand the window only to the edge of the matrix.
						final int cellsPastEdge = targetRow - maxJ();
						markVisited(targetCol, targetRow - cellsPastEdge);
					} // end if
				} // end if

				if ((currentCell.getCol() != maxI())
						&& (currentCell.getRow() != maxJ())) // move to
																// upper-right
																// if possible
				{
					// Either extend full search radius or some fraction until
					// edges of matrix are met.
					final int targetCol = currentCell.getCol() + radius;
					final int targetRow = currentCell.getRow() + radius;

					if ((targetCol <= maxI()) && (targetRow <= maxJ()))
						markVisited(targetCol, targetRow); // radius does not go
															// past the edges of
															// the matrix
					else {
						// Expand the window only to the edge of the matrix.
						final int cellsPastEdge = Math.max(targetCol - maxI(),
								targetRow - maxJ());
						markVisited(targetCol - cellsPastEdge, targetRow
								- cellsPastEdge);
					} // end if
				} // end if

				if (currentCell.getCol() != minI()) // move left if possible
				{
					// Either extend full search radius or some fraction until
					// edges of matrix are met.
					final int targetCol = currentCell.getCol() - radius;
					final int targetRow = currentCell.getRow();

					if (targetCol >= minI())
						markVisited(targetCol, targetRow); // radius does not go
															// past the edges of
															// the matrix
					else {
						// Expand the window only to the edge of the matrix.
						final int cellsPastEdge = minI() - targetCol;
						markVisited(targetCol + cellsPastEdge, targetRow);
					} // end if
				} // end if

				if (currentCell.getCol() != maxI()) // move right if possible
				{
					// Either extend full search radius or some fraction until
					// edges of matrix are met.
					final int targetCol = currentCell.getCol() + radius;
					final int targetRow = currentCell.getRow();

					if (targetCol <= maxI())
						markVisited(targetCol, targetRow); // radius does not go
															// past the edges of
															// the matrix
					else {
						// Expand the window only to the edge of the matrix.
						final int cellsPastEdge = targetCol - maxI();
						markVisited(targetCol - cellsPastEdge, targetRow);
					} // end if
				} // end if

				if ((currentCell.getCol() != minI())
						&& (currentCell.getRow() != minJ())) // move to
																// lower-left if
																// possible
				{
					// Either extend full search radius or some fraction until
					// edges of matrix are met.
					final int targetCol = currentCell.getCol() - radius;
					final int targetRow = currentCell.getRow() - radius;

					if ((targetCol >= minI()) && (targetRow >= minJ()))
						markVisited(targetCol, targetRow); // radius does not go
															// past the edges of
															// the matrix
					else {
						// Expand the window only to the edge of the matrix.
						final int cellsPastEdge = Math.max(minI() - targetCol,
								minJ() - targetRow);
						markVisited(targetCol + cellsPastEdge, targetRow
								+ cellsPastEdge);
					} // end if
				} // end if

				if (currentCell.getRow() != minJ()) // move down if possible
				{
					// Either extend full search radius or some fraction until
					// edges of matrix are met.
					final int targetCol = currentCell.getCol();
					final int targetRow = currentCell.getRow() - radius;

					if (targetRow >= minJ())
						markVisited(targetCol, targetRow); // radius does not go
															// past the edges of
															// the matrix
					else {
						// Expand the window only to the edge of the matrix.
						final int cellsPastEdge = minJ() - targetRow;
						markVisited(targetCol, targetRow + cellsPastEdge);
					} // end if
				} // end if

				if ((currentCell.getCol() != maxI())
						&& (currentCell.getRow() != minJ())) // move to
																// lower-right
																// if possible
				{
					// Either extend full search radius or some fraction until
					// edges of matrix are met.
					final int targetCol = currentCell.getCol() + radius;
					final int targetRow = currentCell.getRow() - radius;

					if ((targetCol <= maxI()) && (targetRow >= minJ()))
						markVisited(targetCol, targetRow); // radius does not go
															// past the edges of
															// the matrix
					else {
						// Expand the window only to the edge of the matrix.
						final int cellsPastEdge = Math.max(targetCol - maxI(),
								minJ() - targetRow);
						markVisited(targetCol - cellsPastEdge, targetRow
								+ cellsPastEdge);
					} // end if
				} // end if
			} // end for loop
		} // end if
	} // end expandWindow(.)

	// Raturns true if the window is modified.
	/**
	 * Mark visited.
	 *
	 * @param col the col
	 * @param row the row
	 */
	protected final void markVisited(int col, int row) {
		if (minValues[col] == -1) // first value is entered in the column
		{
			minValues[col] = row;
			maxValues[col] = row;
			this.size++;
			modCount++; // stucture has been changed
			// return true;
		} else if (minValues[col] > row) // minimum range in the column is
											// expanded
		{
			this.size += minValues[col] - row;
			minValues[col] = row;
			modCount++; // stucture has been changed
		} else if (maxValues[col] < row) // maximum range in the column is
											// expanded
		{
			this.size += row - maxValues[col];
			maxValues[col] = row;
			modCount++;
		} // end if
	} // end markVisited(.)

	// A private class that is a fail-fast iterator through the search window.
	/**
	 * The Class SearchWindowIterator.
	 */
	private final class SearchWindowIterator implements Iterator<Object> {
		// PRIVATE DATA
		/** The current i. */
		private int currentI;
		
		/** The current j. */
		private int currentJ;
		
		/** The window. */
		private final SearchWindow window;
		
		/** The has more elements. */
		private boolean hasMoreElements;
		
		/** The expected mod count. */
		private final int expectedModCount;

		// CONSTRUCTOR
		/**
		 * Instantiates a new search window iterator.
		 *
		 * @param w the w
		 */
		private SearchWindowIterator(SearchWindow w) {
			// Intiialize values
			window = w;
			hasMoreElements = window.size() > 0;
			currentI = window.minI();
			currentJ = window.minJ();
			expectedModCount = w.modCount;
		}

		// PUBLIC FUNCTIONS
		/* 
		 * @see java.util.Iterator#hasNext()
		 */
		/* 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return hasMoreElements;
		}

		/* 
		 * @see java.util.Iterator#next()
		 */
		/* 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Object next() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			else if (!hasMoreElements)
				throw new NoSuchElementException();
			else {
				final ColMajorCell cell = new ColMajorCell(currentI, currentJ);
				if (++currentJ > window.maxJforI(currentI)) {
					if (++currentI <= window.maxI())
						currentJ = window.minJforI(currentI);
					else
						hasMoreElements = false;
				} // end if
				return cell;
			} // end if
		} // end next()

		/* 
		 * @see java.util.Iterator#remove()
		 */
		/* 
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	} // end inner class SearchWindowIterator

} // end SearchWindow
