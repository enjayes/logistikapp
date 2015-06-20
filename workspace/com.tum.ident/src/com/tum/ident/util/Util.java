package com.tum.ident.util;

import java.util.ArrayList;

import com.tum.ident.fastdtw.dtw.FastDTW;
import com.tum.ident.fastdtw.dtw.TimeWarpInfo;
import com.tum.ident.fastdtw.timeseries.TimeSeries;
import com.tum.ident.fastdtw.util.DistanceFunction;
import com.tum.ident.fastdtw.util.DistanceFunctionFactory;


/**
 * The Class Util.
 */
public class Util {

	/**
	 * To string.
	 *
	 * @param <E> the element type
	 * @param list the list
	 * @return the string
	 */
	public static <E> String toString(ArrayList<E> list) {
		String output = "[";
		if (list.size() > 0) {
			for (E listItem : list) {
				output = output + listItem.toString() + ",";
			}
			output.substring(0, output.length() - 1);
		}
		output = output + "]";

		return output;
	}

	/**
	 * To string new line.
	 *
	 * @param <E> the element type
	 * @param list the list
	 * @return the string
	 */
	public static <E> String toStringNewLine(ArrayList<E> list) {
		String output = "";
		if (list.size() > 0) {
			for (E listItem : list) {
				output = output + listItem.toString() + "\n\n";
			}
			output.substring(0, output.length() - 1);
		}
		return output;
	}

	/**
	 * To string filter new line.
	 *
	 * @param <E> the element type
	 * @param list the list
	 * @return the string
	 */
	public static <E> String toStringFilterNewLine(ArrayList<E> list) {
		String output = "";
		if (list.size() > 0) {
			for (E listItem : list) {
				String item = listItem.toString();
				item = item.replaceAll(",id=0", "");
				item = item.replaceAll(",userID=0", "");
				item = item.replaceAll(",deviceID=0", "");
				item = item.replaceAll(",phoneNumberID=0", "");
				output = output + item + "\n\n";
			}
			output.substring(0, output.length() - 1);
		}
		return output;
	}

	/**
	 * Gets the cubic interpolator value.
	 *
	 * @param p the p
	 * @param x the x
	 * @return the cubic interpolator value
	 */
	public static double getCubicInterpolatorValue(double[] p, double x) {
		int xi = Math.min(p.length - 1, Math.max(0, (int) x));
		x -= xi;
		double p0 = p[Math.max(0, xi - 1)];
		double p1 = p[xi];
		double p2 = p[Math.min(p.length - 1, xi + 1)];
		double p3 = p[Math.min(p.length - 1, xi + 2)];
		return p1
				+ 0.5
				* x
				* (p2 - p0 + x
						* (2.0 * p0 - 5.0 * p1 + 4.0 * p2 - p3 + x
								* (3.0 * (p1 - p2) + p3 - p0)));
	}

	/**
	 * Normalize.
	 *
	 * @param data the data
	 * @return the double[]
	 */
	public static double[] normalize(double[] data) {
		if (data != null) {
			if (data.length > 0) {
				double avg = 0;
				for (int i = 0; i < data.length; i++) {
					avg = avg + data[i];
				}
				avg = avg / data.length;
				double v = 0;
				for (int i = 0; i < data.length; i++) {
					double diff = data[i] - avg;
					v += (diff) * (diff);
				}
				v = v / data.length;
				double sd = (float) Math.sqrt(v);
				if (sd > 0) {
					for (int i = 0; i < data.length; i++) {

						data[i] = (data[i] - avg) / sd;
					}
				}
			}
		}
		return data;
	}

	/**
	 * Dwt distance.
	 *
	 * @param data1 the data1
	 * @param data2 the data2
	 * @param radius the radius
	 * @return the double
	 */
	public static double dwtDistance(double[] data1, double[] data2, int radius) {
		double result = -1;
		if (data1 != null && data2 != null) {
			final TimeSeries tsI = new TimeSeries(data1);
			final TimeSeries tsJ = new TimeSeries(data2);
			final DistanceFunction distFn;
			distFn = DistanceFunctionFactory
					.getDistFnByName("ManhattanDistance");
			final TimeWarpInfo info = FastDTW.getWarpInfoBetween(tsI, tsJ,
					radius, distFn);
			result = info.getDistance();
		}
		return result;
	}

	/**
	 * Deg2rad.
	 *
	 * @param deg the deg
	 * @return the double
	 */
	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/**
	 * Rad2deg.
	 *
	 * @param rad the rad
	 * @return the double
	 */
	public static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
