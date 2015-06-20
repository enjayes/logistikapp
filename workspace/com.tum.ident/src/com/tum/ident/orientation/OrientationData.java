package com.tum.ident.orientation;

import android.graphics.Bitmap;
import android.util.Log;

import com.tum.ident.data.DataController;
import com.tum.ident.data.DataItem;
import com.tum.ident.realtime.RealtimeData;
import com.tum.ident.sensors.SensorData;
import com.tum.ident.storage.StorageHandler;


/**
 * The Class OrientationData.
 */
public class OrientationData {

	/** The Constant TAG. */
	private static final String TAG = "OrientationData";

	/** The item. */
	private OrientationItem item = new OrientationItem();
	
	/** The start time. */
	private  long startTime = 0;
	
	/** The wait time. */
	private  long waitTime = 4000;
	
	/** The sensor data. */
	private SensorData sensorData = null;

	/** The last orientation. */
	private float[] lastOrientation = null;
	
	/** The last orientation set. */
	private boolean lastOrientationSet = false;
	
	/** The listening. */
	private boolean listening = false;
	
	/** The movement threshold. */
	private float movementThreshold = 0.1f;
	
	/** The movement threshold x. */
	private float movementThresholdX = 0.9f;
	
	/** The data controller. */
	private DataController dataController;

	/** The bmp. */
	private Bitmap bmp = null;

	/**
	 * Instantiates a new orientation data.
	 *
	 * @param dataController the data controller
	 */
	public OrientationData(DataController dataController) {
		load();
		
		
		this.dataController = dataController;
	}

	/**
	 * Sets the sensor data.
	 *
	 * @param sensorData the new sensor data
	 */
	public void setSensorData(SensorData sensorData) {
		this.sensorData = sensorData;
	}

	/**
	 * Gets the data item.
	 *
	 * @return the data item
	 */
	public DataItem getDataItem() {
		return new DataItem("", item);
	}

	/**
	 * Start listening.
	 */
	public void startListening() {
		startTime = System.currentTimeMillis();
		lastOrientation = new float[3];
		lastOrientationSet = false;
		listening = true;
	}

	/**
	 * Distance.
	 *
	 * @param a the a
	 * @param b the b
	 * @return the float
	 */
	public static float distance(float a, float b) {
		float distance = Math.abs(a - b);
		if (distance > Math.PI) {
			distance = (float) Math.abs(Math.PI * 2 - distance);
		}
		return distance;
	}

	/**
	 * Gets the orientation image.
	 *
	 * @return the orientation image
	 */
	public Bitmap getOrientationImage() {
		bmp = item.getImage(bmp);
		return bmp;
	}

	/**
	 * On sensor changed.
	 *
	 * @param mOrientation the m orientation
	 */
	public void onSensorChanged(float[] mOrientation) {
		if (listening) {
			if (System.currentTimeMillis() - startTime < waitTime) {
				if (lastOrientationSet == false) {
					lastOrientationSet = true;
				} else {
					Log.v(TAG, "o: " + (mOrientation[0]) + " ,"
							+ (mOrientation[1]) + " , " + (mOrientation[2]));
					Log.v(TAG, "l: " + (lastOrientation[0]) + " ,"
							+ (lastOrientation[1]) + " , "
							+ (lastOrientation[2]));

					Log.v(TAG, "-> " + (mOrientation[0] - lastOrientation[0])
							+ " ," + (mOrientation[1] - lastOrientation[1])
							+ " , " + (mOrientation[2] - lastOrientation[2]));
					if (distance(mOrientation[0], lastOrientation[0]) > movementThresholdX) {
						sensorData.unregisterOrientationListeners();
						Log.v(TAG, "->moved!");
						listening = false;
						lastOrientationSet = false;
					} else if (distance(mOrientation[1], lastOrientation[1]) > movementThreshold) {
						sensorData.unregisterOrientationListeners();
						Log.v(TAG, "->moved!");
						listening = false;
						lastOrientationSet = false;
					} else if (distance(mOrientation[2], lastOrientation[2]) > movementThreshold) {
						sensorData.unregisterOrientationListeners();
						Log.v(TAG, "->moved!");
						listening = false;
						lastOrientationSet = false;
					}
				}
				if (listening) {
					System.arraycopy(mOrientation, 0, lastOrientation, 0,
							lastOrientation.length);
				}

			} else {
				if (lastOrientationSet == false) {
					System.arraycopy(mOrientation, 0, lastOrientation, 0,
							lastOrientation.length);
				}

				float[] newOrientation = new float[3];

				System.arraycopy(lastOrientation, 0, newOrientation, 0,
						newOrientation.length);

				Log.v(TAG, "STORE: " + (lastOrientation[0]) + " ,"
						+ (lastOrientation[1]) + " , " + (lastOrientation[2]));

				item.add(newOrientation);

				dataController.addData("", item);
				sensorData.unregisterOrientationListeners();
				listening = false;
				lastOrientationSet = false;
				save();
				startTime = System.currentTimeMillis();
			}
		}
	}

	/**
	 * Load.
	 */
	public void load() {

		String fileName = "orientations.ser";

		Log.v(TAG, "Load Locations:" + fileName);

		item = null;

		item = (OrientationItem) StorageHandler.loadObject(fileName);
		if (item == null) {
			item = new OrientationItem();
		}
	}

	/**
	 * Save.
	 */
	public void save() {

		String fileName = "orientations.ser";

		Log.v(TAG, "Save Locations:" + fileName);

		StorageHandler.saveObject(item, fileName);

	}

}