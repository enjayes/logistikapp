/*
 * FullWindow.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.dtw;

import com.tum.ident.fastdtw.timeseries.TimeSeries;



/**
 * The Class FullWindow.
 */
public class FullWindow extends SearchWindow {

	// CONSTRUCTOR
	/**
	 * Instantiates a new full window.
	 *
	 * @param tsI the ts i
	 * @param tsJ the ts j
	 */
	public FullWindow(TimeSeries tsI, TimeSeries tsJ) {
		super(tsI.size(), tsJ.size());

		for (int i = 0; i < tsI.size(); i++) {
			super.markVisited(i, minJ());
			super.markVisited(i, maxJ());
		} // end for loop
	} // end CONSTRUCTOR

} // end class FullWindow
