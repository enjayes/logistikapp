/*
 * Arrays.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.util;



/**
 * The Interface DistanceFunction.
 */
public interface DistanceFunction {
	
	/**
	 * Calc distance.
	 *
	 * @param vector1 the vector1
	 * @param vector2 the vector2
	 * @return the double
	 */
	public double calcDistance(double[] vector1, double[] vector2);
}