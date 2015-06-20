/*
 * WarpPathWindow.java   Jul 14, 2004
 *
 * PROJECT DESCRIPTION
 */

package com.tum.ident.fastdtw.dtw;



/**
 * This class...
 *
 * @author Stan Salvador, stansalvador@hotmail.com
 * @version last changed: Jun 30, 2004
 * @see
 * @since Jun 30, 2004
 */

public class WarpPathWindow extends SearchWindow {

	// CONSTANTS
	/** The Constant defaultRadius. */
	@SuppressWarnings("unused")
	private final static int defaultRadius = 0;

	// CONSTRUCTORS
	/**
	 * Instantiates a new warp path window.
	 *
	 * @param path the path
	 * @param searchRadius the search radius
	 */
	public WarpPathWindow(WarpPath path, int searchRadius) {
		super(path.get(path.size() - 1).getCol() + 1, path.get(path.size() - 1)
				.getRow() + 1);

		for (int p = 0; p < path.size(); p++)
			super.markVisited(path.get(p).getCol(), path.get(p).getRow());

		super.expandWindow(searchRadius);
	} // end Constructor

} // end class WarpPathWindow
