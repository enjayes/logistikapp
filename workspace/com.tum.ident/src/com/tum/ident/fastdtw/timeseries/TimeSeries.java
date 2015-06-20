/*
 * TimeSeries.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.tum.ident.fastdtw.timeseries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import com.tum.ident.fastdtw.util.Arrays;



/**
 * The Class TimeSeries.
 */
public class TimeSeries {
	
	/** The Constant ZERO_ARRAY. */
	private static final int[] ZERO_ARRAY = new int[0];
	
	/** The Constant DEFAULT_IS_TIME_1ST_COL. */
	private static final boolean DEFAULT_IS_TIME_1ST_COL = true;
	
	/** The Constant DEFAULT_DELIMITER. */
	private static final char DEFAULT_DELIMITER = ',';
	
	/** The Constant DEFAULT_IS_LABELED. */
	private static final boolean DEFAULT_IS_LABELED = true;

	/** The labels. */
	public ArrayList<String> labels; // labels for each column
	
	/** The time readings. */
	public ArrayList<Double> timeReadings; // ArrayList of Double
	
	/** The ts array. */
	public ArrayList<TimeSeriesPoint> tsArray; // ArrayList of TimeSeriesPoint..
												// no time

	// TODO don't use defaults delimiter/1stColTime... determine if not
	// specified

	// CONSTRUCTORS // TODO method to peek at determined delimiter, 1st col time
	/**
												 * Instantiates a new time series.
												 */
												TimeSeries() {
		labels = new ArrayList<String>(); // TODO isLabeled constuctor options?
		timeReadings = new ArrayList<Double>();
		tsArray = new ArrayList<TimeSeriesPoint>();
	}

	/**
	 * Instantiates a new time series.
	 *
	 * @param numOfDimensions the num of dimensions
	 */
	public TimeSeries(int numOfDimensions) {
		this();
		labels.add("Time");
		for (int x = 0; x < numOfDimensions; x++)
			labels.add("" + x);
	}

	// Copy Constructor
	/**
	 * Instantiates a new time series.
	 *
	 * @param origTS the orig ts
	 */
	public TimeSeries(TimeSeries origTS) {
		labels = new ArrayList<String>(origTS.labels);
		timeReadings = new ArrayList<Double>(origTS.timeReadings);
		tsArray = new ArrayList<TimeSeriesPoint>(origTS.tsArray);
	}

	/**
	 * Instantiates a new time series.
	 *
	 * @param inputFile the input file
	 * @param isFirstColTime the is first col time
	 */
	public TimeSeries(String inputFile, boolean isFirstColTime) {
		this(inputFile, ZERO_ARRAY, isFirstColTime);
	}

	/**
	 * Instantiates a new time series.
	 *
	 * @param inputFile the input file
	 * @param delimiter the delimiter
	 */
	public TimeSeries(String inputFile, char delimiter) {
		this(inputFile, ZERO_ARRAY, DEFAULT_IS_TIME_1ST_COL,
				DEFAULT_IS_LABELED, delimiter);
	}

	/**
	 * Instantiates a new time series.
	 *
	 * @param inputFile the input file
	 * @param isFirstColTime the is first col time
	 * @param delimiter the delimiter
	 */
	public TimeSeries(String inputFile, boolean isFirstColTime, char delimiter) {
		this(inputFile, ZERO_ARRAY, isFirstColTime, DEFAULT_IS_LABELED,
				delimiter);
	}

	/**
	 * Instantiates a new time series.
	 *
	 * @param inputFile the input file
	 * @param isFirstColTime the is first col time
	 * @param isLabeled the is labeled
	 * @param delimiter the delimiter
	 */
	public TimeSeries(String inputFile, boolean isFirstColTime,
			boolean isLabeled, char delimiter) {
		this(inputFile, ZERO_ARRAY, isFirstColTime, isLabeled, delimiter);
	}

	/**
	 * Instantiates a new time series.
	 *
	 * @param inputFile the input file
	 * @param colToInclude the col to include
	 * @param isFirstColTime the is first col time
	 */
	public TimeSeries(String inputFile, int[] colToInclude,
			boolean isFirstColTime) {
		this(inputFile, colToInclude, isFirstColTime, DEFAULT_IS_LABELED,
				DEFAULT_DELIMITER);
	}

	/**
	 * Instantiates a new time series.
	 *
	 * @param inputFile the input file
	 * @param colToInclude the col to include
	 * @param isFirstColTime the is first col time
	 * @param isLabeled the is labeled
	 * @param delimiter the delimiter
	 */
	@SuppressWarnings("resource")
	public TimeSeries(String inputFile, int[] colToInclude,
			boolean isFirstColTime, boolean isLabeled, char delimiter) {
		this();

		try {
			// Record the Label names (fropm the top row.of the input file).
			BufferedReader br = new BufferedReader(new FileReader(inputFile)); // open
																				// the
																				// input
																				// file
			String line = br.readLine(); // the top row that contains attribiute
											// names.
			StringTokenizer st = new StringTokenizer(line,
					String.valueOf(delimiter));

			if (isLabeled) {
				int currentCol = 0;
				while (st.hasMoreTokens()) {
					final String currentToken = st.nextToken();
					if ((colToInclude.length == 0)
							|| (Arrays.contains(colToInclude, currentCol)))
						labels.add(currentToken);

					currentCol++;
				} // end while loop

				// Make sure that the first column is labeled is for Time.
				if (labels.size() == 0)
					throw new RuntimeException(
							"ERROR:  The first row must contain label "
									+ "information, it is empty!");
				else if (!isFirstColTime)
					labels.add(0, "Time");
				else if (isFirstColTime
						&& !labels.get(0).equalsIgnoreCase("Time"))
					throw new RuntimeException(
							"ERROR:  The time column (1st col) in a time series must be labeled as 'Time', '"
									+ labels.get(0) + "' was found instead");
			} else // time series file is not labeled
			{
				if ((colToInclude == null) || (colToInclude.length == 0)) {
					labels.add("Time");
					if (isFirstColTime)
						st.nextToken();

					int currentCol = 1; // TODO input fails gracefully
					while (st.hasMoreTokens()) {
						st.nextToken();
						labels.add(new String("c" + currentCol++)); // TODO add
																	// measurement
																	// with no
																	// time
					}
				} else {
					java.util.Arrays.sort(colToInclude);
					labels.add("Time");
					for (int c = 0; c < colToInclude.length; c++)
						if (colToInclude[c] > 0)
							labels.add(new String("c" + c)); // TODO change to
																// letterNum
				} // end if

				// Close and re-open the file.
				br.close();
				br = new BufferedReader(new FileReader(inputFile)); // open the
																	// input
																	// file
			} // end if

			// Read in all of the values in the data file.
			while ((line = br.readLine()) != null) // read lines until end of
													// file
			{
				if (line.length() > 0) // ignore empty lines
				{
					st = new StringTokenizer(line, ",");

					// Make sure that the current line has the correct number of
					// currentLineValues in it.
					// if (st.countTokens() != (labels.size()+ignoredCol))
					// throw new RuntimeException("ERROR:  Line " +
					// (tsArray.size()+1) +
					// "contains the wrong number of currentLineValues. " +
					// "expected:  " + (labels.size()+ignoredCol) + ", " +
					// "found: " + st.countTokens());

					// Read all currentLineValues in the current line
					final ArrayList<Double> currentLineValues = new ArrayList<Double>();
					int currentCol = 0;
					while (st.hasMoreTokens()) {
						final String currentToken = st.nextToken();
						if ((colToInclude.length == 0)
								|| (Arrays.contains(colToInclude, currentCol))) {
							// Attempt to parse the next value to a Double
							// value.
							final Double nextValue;
							try {
								nextValue = Double.valueOf(currentToken);
							} catch (NumberFormatException e) {
								throw new RuntimeException("ERROR:  '"
										+ currentToken
										+ "' is not a valid number");
							}

							currentLineValues.add(nextValue);
						} // end if

						currentCol++;
					} // end while loop

					// Update the private data with the current Row that has
					// been
					// read.
					if (isFirstColTime)
						timeReadings.add(currentLineValues.get(0));
					else
						timeReadings.add(Double.valueOf(timeReadings.size()));
					final int firstMeasurement;
					if (isFirstColTime)
						firstMeasurement = 1;
					else
						firstMeasurement = 0;
					final TimeSeriesPoint readings = new TimeSeriesPoint(
							currentLineValues.subList(firstMeasurement,
									currentLineValues.size()));
					tsArray.add(readings);
					// timeValueMap.put(timeReadings.get(timeReadings.size()-1),
					// readings);
				} // end if
			} // end while loop
			br.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("ERROR:  The file '" + inputFile
					+ "' was not found.");
		} catch (IOException e) {
			throw new RuntimeException("ERROR:  Problem reading the file '"
					+ inputFile + "'.");
		} // end try block
	} // end constructor

	/**
	 * Instantiates a new time series.
	 *
	 * @param values the values
	 */
	public TimeSeries(double values[]) {
		this();
		if (values != null) {
			labels.add("Value");
			for (int j = 0; j < values.length; j++) {
				timeReadings.add(Double.valueOf(timeReadings.size()));
				final TimeSeriesPoint readings = new TimeSeriesPoint(values);
				tsArray.add(readings);
			}
		}

	} // end constructor

	// FUNCTIONS
	/**
	 * Save.
	 *
	 * @param outFile the out file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void save(File outFile) throws IOException {
		final PrintWriter out = new PrintWriter(new FileOutputStream(outFile));
		out.write(this.toString());
		out.flush();
		out.close();
	}

	/**
	 * Clear.
	 */
	public void clear() {
		labels.clear();
		timeReadings.clear();
		tsArray.clear();
		// timeValueMap.clear();
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return timeReadings.size();
	}

	/**
	 * Num of pts.
	 *
	 * @return the int
	 */
	public int numOfPts() {
		return this.size();
	}

	/**
	 * Num of dimensions.
	 *
	 * @return the int
	 */
	public int numOfDimensions() {
		return labels.size() - 1;
	}

	/**
	 * Gets the time at nth point.
	 *
	 * @param n the n
	 * @return the time at nth point
	 */
	public double getTimeAtNthPoint(int n) {
		return timeReadings.get(n).doubleValue();
	}

	/**
	 * Gets the label.
	 *
	 * @param index the index
	 * @return the label
	 */
	public String getLabel(int index) {
		return labels.get(index);
	}

	/**
	 * Gets the labels arr.
	 *
	 * @return the labels arr
	 */
	public String[] getLabelsArr() {
		final String[] labelArr = new String[labels.size()];
		for (int x = 0; x < labels.size(); x++)
			labelArr[x] = labels.get(x);
		return labelArr;
	}

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public ArrayList<String> getLabels() {
		return labels;
	}

	/**
	 * Sets the labels.
	 *
	 * @param newLabels the new labels
	 */
	public void setLabels(String[] newLabels) {
		labels.clear();
		for (int x = 0; x < newLabels.length; x++)
			labels.add(newLabels[x]);
	}

	/**
	 * Sets the labels.
	 *
	 * @param newLabels the new labels
	 */
	public void setLabels(ArrayList<String> newLabels) {
		labels.clear();
		for (int x = 0; x < newLabels.size(); x++)
			labels.add(newLabels.get(x));
	}

	/**
	 * Gets the measurement.
	 *
	 * @param pointIndex the point index
	 * @param valueIndex the value index
	 * @return the measurement
	 */
	public double getMeasurement(int pointIndex, int valueIndex) {
		return tsArray.get(pointIndex).get(valueIndex);
	}

	/**
	 * Gets the measurement.
	 *
	 * @param pointIndex the point index
	 * @param valueLabel the value label
	 * @return the measurement
	 */
	public double getMeasurement(int pointIndex, String valueLabel) {
		final int valueIndex = labels.indexOf(valueLabel);
		if (valueIndex < 0)
			throw new RuntimeException("ERROR:  the label '" + valueLabel
					+ "' was " + "not one of:  " + labels);

		return tsArray.get(pointIndex).get(valueIndex - 1);
	}

	/**
	 * Gets the measurement vector.
	 *
	 * @param pointIndex the point index
	 * @return the measurement vector
	 */
	public double[] getMeasurementVector(int pointIndex) {
		return tsArray.get(pointIndex).toArray();
	}

	/**
	 * Sets the measurement.
	 *
	 * @param pointIndex the point index
	 * @param valueIndex the value index
	 * @param newValue the new value
	 */
	public void setMeasurement(int pointIndex, int valueIndex, double newValue) {
		tsArray.get(pointIndex).set(valueIndex, newValue);
	}

	/**
	 * Adds the first.
	 *
	 * @param time the time
	 * @param values the values
	 */
	public void addFirst(double time, TimeSeriesPoint values) {
		if (labels.size() != values.size() + 1) // labels include a label for
												// time
			throw new RuntimeException("ERROR:  The TimeSeriesPoint: " + values
					+ " contains the wrong number of values. " + "expected:  "
					+ labels.size() + ", " + "found: " + values.size());

		if (time >= timeReadings.get(0).doubleValue())
			throw new RuntimeException(
					"ERROR:  The point being inserted into the "
							+ "beginning of the time series does not have "
							+ "the correct time sequence. ");

		timeReadings.add(0, Double.valueOf(time));
		tsArray.add(0, values);
	} // end addFirst(..)

	/**
	 * Adds the last.
	 *
	 * @param time the time
	 * @param values the values
	 */
	public void addLast(double time, TimeSeriesPoint values) {
		if (labels.size() != values.size() + 1) // labels include a label for
												// time
			throw new RuntimeException("ERROR:  The TimeSeriesPoint: " + values
					+ " contains the wrong number of values. " + "expected:  "
					+ labels.size() + ", " + "found: " + values.size());

		if ((this.size() > 0)
				&& (time <= timeReadings.get(timeReadings.size() - 1)
						.doubleValue()))
			throw new RuntimeException(
					"ERROR:  The point being inserted at the "
							+ "end of the time series does not have "
							+ "the correct time sequence. ");

		timeReadings.add(Double.valueOf(time));
		tsArray.add(values);
	} // end addLast(..)

	/**
	 * Removes the first.
	 */
	public void removeFirst() {
		if (this.size() == 0)
			System.err
					.println("WARNING:  TimeSeriesPoint:removeFirst() called on an empty time series!");
		else {
			timeReadings.remove(0);
			tsArray.remove(0);
		} // end if
	} // end removeFirst()

	/**
	 * Removes the last.
	 */
	public void removeLast() {
		if (this.size() == 0)
			System.err
					.println("WARNING:  TimeSeriesPoint:removeLast() called on an empty time series!");
		else {
			tsArray.remove(timeReadings.size() - 1);
			timeReadings.remove(timeReadings.size() - 1);
		} // end if
	} // end removeFirst()

	/**
	 * Normalize.
	 */
	public void normalize() {
		// Calculate the mean of each FD.
		final double[] mean = new double[this.numOfDimensions()];
		for (int col = 0; col < numOfDimensions(); col++) {
			double currentSum = 0.0;
			for (int row = 0; row < this.size(); row++)
				currentSum += this.getMeasurement(row, col);

			mean[col] = currentSum / this.size();
		} // end for loop

		// Calculate the standard deviation of each FD.
		final double[] stdDev = new double[numOfDimensions()];
		for (int col = 0; col < numOfDimensions(); col++) {
			double variance = 0.0;
			for (int row = 0; row < this.size(); row++)
				variance += Math.abs(getMeasurement(row, col) - mean[col]);

			stdDev[col] = variance / this.size();
		} // end for loop

		// Normalize the values in the data using the mean and standard
		// deviation
		// for each FD. => Xrc = (Xrc-Mc)/SDc
		for (int row = 0; row < this.size(); row++) {
			for (int col = 0; col < numOfDimensions(); col++) {
				// Normalize data point.
				if (stdDev[col] == 0.0) // prevent divide by zero errors
					setMeasurement(row, col, 0.0); // stdDev is zero means all
													// pts identical
				else
					// typical case
					setMeasurement(row, col,
							(getMeasurement(row, col) - mean[col])
									/ stdDev[col]);
			} // end for loop
		} // end for loop
	} // end normalize();

	/* 
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer outStr = new StringBuffer();
		/*
		 * // Write labels for (int x=0; x<labels.size(); x++) {
		 * outStr.append(labels.get(x)); if (x < labels.size()-1)
		 * outStr.append(","); else outStr.append("\n"); } // end for loop
		 */
		// Write the data for each row.
		for (int r = 0; r < timeReadings.size(); r++) {
			// Time
			// outStr.append(timeReadings.get(r).toString());

			// The rest of the value on the row.
			final TimeSeriesPoint values = tsArray.get(r);
			for (int c = 0; c < values.size(); c++)
				outStr.append(values.get(c));

			if (r < timeReadings.size() - 1)
				outStr.append("\n");
		} // end for loop

		return outStr.toString();
	} // end toString()

	// Returns the first non-digit (and not a '.') character in a file under the
	// assumption that it is the delimiter in the file.
	/**
	 * Determine delimiter.
	 *
	 * @param filePath the file path
	 * @return the char
	 */
	@SuppressWarnings("unused")
	private static char determineDelimiter(String filePath) {
		final char DEFAULT_DELIMITER = ',';

		try {
			final BufferedReader in = new BufferedReader(new FileReader(
					filePath));

			String line = in.readLine().trim(); // read first line

			if (!Character.isDigit(line.charAt(0))) // go to 2nd line if 1st
													// line appears to be labels
				line = in.readLine();

			in.close();

			// Searches the 2nd line of the file until a non-number character is
			// found. The delimiter is assumed to be that character.
			// numbers, minus signs, periods, and 'E' (exponent) are accepted
			// number characters.
			for (int x = 0; x < line.length(); x++) {
				if (!Character.isDigit(line.charAt(x))
						&& (line.charAt(x) != '.') && (line.charAt(x) != '-')
						&& (Character.toUpperCase(line.charAt(x)) != 'E'))
					return line.charAt(x);
			}

			// No delimiters were found, which means that there must be only one
			// column
			// A delimiter does not need to be known to read this file.
			return DEFAULT_DELIMITER;
		} catch (IOException e) {
			return DEFAULT_DELIMITER;
		}
	} // end determineDelimiter(.)

	/**
	 * Extract first number.
	 *
	 * @param str the str
	 * @return the double
	 */
	private static double extractFirstNumber(String str) {
		StringBuffer numStr = new StringBuffer();

		// Keep adding characters onto numStr until a non-number character
		// is reached.
		for (int x = 0; x < str.length(); x++) {
			if ((Character.isDigit(str.charAt(x))) || (str.charAt(x) == '.')
					|| (str.charAt(x) == '-')
					|| (Character.toUpperCase(str.charAt(x)) == 'E'))
				numStr.append(str.charAt(x));
			else
				Double.parseDouble(numStr.toString());
		} // end for loop

		return -1;
	}

	// Automatically determines if the first column in a file is time
	// measurements.
	// It assumes that a column of time will have equal spacing between all
	// values.
	/**
	 * Determine is first col time.
	 *
	 * @param filePath the file path
	 * @return true, if successful
	 */
	@SuppressWarnings("unused")
	private static boolean determineIsFirstColTime(String filePath) {
		final boolean DEFAULT_VALUE = false;

		try {
			final BufferedReader in = new BufferedReader(new FileReader(
					filePath));

			// This parameter is the percentage of flexibility that is permitted
			// from
			// a perfectly even distribution of time values. This function will
			// most likely not work if this is set to zero because of floating-
			// point math roundoff errors.
			// (a setting of '0.05' is '5 percent')
			final double EQUALITY_FLEXIBILITY_PCT = 0.001;

			final int NUM_OF_VALUES_TO_CMP = 100; // $ of time values to look
													// examine

			final Vector<Double> possibleTimeValues = new Vector<Double>(
					NUM_OF_VALUES_TO_CMP); // 'stores numOfValuesToCompare'
											// values

			// Read the first 'numOfValuesToCompare' possible time values from
			// the file
			// and store them in 'possibleTimeValues'.
			String line = in.readLine();

			while ((possibleTimeValues.size() < NUM_OF_VALUES_TO_CMP)
					&& ((line = in.readLine()) != null))
				possibleTimeValues
						.add(Double.valueOf(extractFirstNumber(line)));

			if (possibleTimeValues.size() <= 1) {
				in.close();
				return DEFAULT_VALUE;
			}

			// See if there is equal spacing (with a flexibility of
			// 'equalityFlexibilityFactor') between all values in // TODO
			// TimeSeries is now messy...in need of design
			// 'possibleTimeValues'.
			if ((possibleTimeValues.size() > 1)
					&& possibleTimeValues.get(1).equals(
							possibleTimeValues.get(0))) {
				in.close();
				return DEFAULT_VALUE; // special case needed for very flat data
			}

			final double expectedDiff = possibleTimeValues.get(1).doubleValue()
					- possibleTimeValues.get(0).doubleValue();
			final double flexibility = expectedDiff * EQUALITY_FLEXIBILITY_PCT;
			for (int x = 1; x < possibleTimeValues.size(); x++) {
				if (Math.abs(possibleTimeValues.get(x).doubleValue()
						- possibleTimeValues.get(x - 1).doubleValue()
						- expectedDiff) > Math.abs(flexibility)) {
					in.close();
					return false;
				}
			} // end for loop

			in.close();
			return true;
		} catch (IOException e) {
			return DEFAULT_VALUE;
		}
	} // end determineIsFirstColTime(.)

	/**
	 * Sets the max capacity.
	 *
	 * @param capacity the new max capacity
	 */
	protected void setMaxCapacity(int capacity) {
		this.timeReadings.ensureCapacity(capacity);
		this.tsArray.ensureCapacity(capacity);
	}

} // end class TimeSeries
