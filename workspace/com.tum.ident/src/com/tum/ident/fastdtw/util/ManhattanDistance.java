/*
 * Arrays.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.util;



/**
 * The Class ManhattanDistance.
 */
public class ManhattanDistance implements DistanceFunction {
	
	/**
	 * Instantiates a new manhattan distance.
	 */
	public ManhattanDistance() {

	}

	/* 
	 * @see com.tum.ident.fastdtw.util.DistanceFunction#calcDistance(double[], double[])
	 */
	/* 
	 * @see com.tum.ident.fastdtw.util.DistanceFunction#calcDistance(double[], double[])
	 */
	@Override
	public double calcDistance(double[] vector1, double[] vector2) {
		if (vector1.length != vector2.length)
			throw new RuntimeException("ERROR:  cannot calculate the distance "
					+ "between vectors of different sizes.");

		double diffSum = 0.0;
		for (int x = 0; x < vector1.length; x++)
			diffSum += Math.abs(vector1[x] - vector2[x]);

		return diffSum;
	}

}