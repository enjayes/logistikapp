/*
 * TimeSeriesPoint.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.timeseries;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;



/**
 * The Class TimeSeriesPoint.
 */
public class TimeSeriesPoint {
	// PRIVATE DATA
	/** The measurements. */
	private double[] measurements;
	
	/** The hash code. */
	private int hashCode;

	// CONSTRUCTORS
	/**
	 * Instantiates a new time series point.
	 *
	 * @param values the values
	 */
	public TimeSeriesPoint(double[] values) {
		hashCode = 0;
		measurements = new double[values.length];
		for (int x = 0; x < values.length; x++) {
			hashCode += new Double(values[x]).hashCode();
			measurements[x] = values[x];
		}
	}

	/**
	 * Instantiates a new time series point.
	 *
	 * @param values the values
	 */
	public TimeSeriesPoint(Collection<?> values) {
		measurements = new double[values.size()];
		hashCode = 0;

		final Iterator<?> i = values.iterator();
		int index = 0;
		while (i.hasNext()) {
			final Object nextElement = i.next();
			if (nextElement instanceof Double)
				measurements[index] = ((Double) nextElement).doubleValue();
			else if (nextElement instanceof Integer)
				measurements[index] = ((Integer) nextElement).doubleValue();
			else if (nextElement instanceof BigInteger)
				measurements[index] = ((BigInteger) nextElement).doubleValue();
			else
				throw new RuntimeException("ERROR:  The element " + nextElement
						+ " is not a valid numeric type");

			hashCode += new Double(measurements[index]).hashCode();
			index++;
		} // end while loop
	} // end constructor

	// FUNCTIONS
	/**
	 * Gets the.
	 *
	 * @param dimension the dimension
	 * @return the double
	 */
	public double get(int dimension) {
		return measurements[dimension];
	}

	/**
	 * Sets the.
	 *
	 * @param dimension the dimension
	 * @param newValue the new value
	 */
	public void set(int dimension, double newValue) {
		hashCode -= new Double(measurements[dimension]).hashCode();
		measurements[dimension] = newValue;
		hashCode += new Double(newValue).hashCode();
	}

	/**
	 * To array.
	 *
	 * @return the double[]
	 */
	public double[] toArray() {
		return measurements;
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return measurements.length;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String outStr = "(";
		for (int x = 0; x < measurements.length; x++) {
			outStr += measurements[x];
			if (x < measurements.length - 1)
				outStr += ",";
		}
		outStr += ")";

		return outStr;
	} // end toString()

	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		else if (o instanceof TimeSeriesPoint) {
			final double[] testValues = ((TimeSeriesPoint) o).toArray();
			if (testValues.length == measurements.length) {
				for (int x = 0; x < measurements.length; x++)
					if (measurements[x] != testValues[x])
						return false;

				return true;
			} else
				return false;
		} else
			return false;
	} // end public boolean equals

	/* 
	 * @see java.lang.Object#hashCode()
	 */
	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.hashCode;
	}

} // end class TimeSeriesPoint
