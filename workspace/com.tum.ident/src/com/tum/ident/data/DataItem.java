package com.tum.ident.data;

import java.io.Serializable;

import android.util.Log;

import com.tum.ident.battery.BatteryItemList;
import com.tum.ident.camera.CameraPixelList;
import com.tum.ident.camera.DarkFrame;
import com.tum.ident.data.DataController.DataType;
import com.tum.ident.files.FileItemList;
import com.tum.ident.gait.StepStatistics;
import com.tum.ident.identification.IdentificationItem;
import com.tum.ident.locations.LocationAreaList;
import com.tum.ident.music.MusicItemList;
import com.tum.ident.orientation.OrientationItem;
import com.tum.ident.spectrum.SpectrumItemList;


/**
 * The Class DataItem.
 */
public class DataItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TAG. */
	@SuppressWarnings("unused")
	transient private final static String TAG = "DataItem";
	
	/** The last error. */
	transient private static String lastError = "";

	/** The data. */
	private byte[] data;
	
	/** The type. */
	private DataType type;
	
	/** The parameter. */
	private String parameter;


	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public DataType getType() {
		return type;
	}
	
	/**
	 * Gets the type index.
	 *
	 * @return the type index
	 */
	public int getTypeIndex() {
		return type.ordinal();
	}

	/**
	 * Gets the parameter.
	 *
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * Sets the parameter.
	 *
	 * @param parameter the new parameter
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * Clear.
	 */
	public void clear(){
		data = null;
		parameter = "";
	}

	/**
	 * Gets the last error.
	 *
	 * @return the last error
	 */
	public static String getLastError() {
		return lastError;
	}
	
	
	/**
	 * Gets the data type.
	 *
	 * @param obj the obj
	 * @return the data type
	 */
	public static DataType getDataType(Object obj) {
		if (obj instanceof BatteryItemList) {
			return DataType.Battery;
		} else if (obj instanceof SpectrumItemList) {
			return DataType.Spectrum;
		} else if (obj instanceof OrientationItem) {
			return DataType.Orientation;
		} else if (obj instanceof FileItemList) {
			return DataType.File;
		} else if (obj instanceof MusicItemList) {
			return DataType.Music;
		} else if (obj instanceof IdentificationItem) {
			return DataType.UserDevice;
		} else if (obj instanceof StepStatistics) {
			return DataType.StepDetection;
		} else if (obj instanceof LocationAreaList) {
			return DataType.Location;
		} else if (obj instanceof CameraPixelList) {
			CameraPixelList cameraPixels = (CameraPixelList) obj;
			if (cameraPixels.getIndex() == 0) {
				return DataType.PixelErrorBack;
			} else {
				return DataType.PixelErrorFront;
			}
		} else if (obj instanceof DarkFrame) {
			DarkFrame darkFrame = (DarkFrame) obj;
			if (darkFrame.getIndex() == 0) {
				return DataType.DarkFrameBack;
			} else {
				return DataType.DarkFrameFront;
			}

		} else {
			Log.v("DEBUG", "CLASS NOT FOUND: " + obj);
		}
		return null;
	}

	/**
	 * Instantiates a new data item.
	 *
	 * @param parameter the parameter
	 * @param data the data
	 * @param type the type
	 */
	public DataItem(String parameter, byte[] data, DataType type) {
		this.parameter = parameter;
		this.data = data;
		this.type = type;
	}

	/*
	 * public DataItem(String parameter,Object dataObject,DataType type) { byte
	 * data[]= getData(dataObject); this.parameter = parameter; this.data =
	 * data; this.type = type; }
	 */

	/**
	 * Instantiates a new data item.
	 *
	 * @param parameter the parameter
	 * @param dataObject the data object
	 */
	public DataItem(String parameter, Object dataObject) {
		this.parameter = parameter;
		this.type = getDataType(dataObject);
		Log.v("DEBUG", "getData: " + this.type);
		this.data = DataFactory.getData(dataObject);

	}


}
