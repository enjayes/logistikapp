/*
 * LinearWindow.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.dtw;

import com.tum.ident.fastdtw.timeseries.TimeSeries;



/**
 * The Class LinearWindow.
 */
public class LinearWindow extends SearchWindow {
	// CONSTANTS
	/** The Constant DEFAULT_RADIUS. */
	private final static int DEFAULT_RADIUS = 0;

	// CONSTRUCTORS
	/**
	 * Instantiates a new linear window.
	 *
	 * @param tsI the ts i
	 * @param tsJ the ts j
	 * @param searchRadius the search radius
	 */
	public LinearWindow(TimeSeries tsI, TimeSeries tsJ, int searchRadius) {
		super(tsI.size(), tsJ.size());

		final double ijRatio = (double) tsI.size() / (double) tsJ.size();
		final boolean isIlargest = tsI.size() >= tsJ.size();
		for (int i = 0; i < tsI.size(); i++) {
			if (isIlargest) {
				final int j = Math.min((int) Math.round((i) / ijRatio),
						tsJ.size() - 1);
				super.markVisited(i, j);
			} else {
				final int maxJ = ((int) Math.round((i + 1) / ijRatio)) - 1;
				final int minJ = ((int) Math.round((i) / ijRatio));
				super.markVisited(i, minJ);
				super.markVisited(i, maxJ);
			} // end if
		} // end for loop

		super.expandWindow(searchRadius);
	} // end Constructor

	/**
	 * Instantiates a new linear window.
	 *
	 * @param tsI the ts i
	 * @param tsJ the ts j
	 */
	public LinearWindow(TimeSeries tsI, TimeSeries tsJ) {
		this(tsI, tsJ, DEFAULT_RADIUS);
	}

} // end class FullWindow

