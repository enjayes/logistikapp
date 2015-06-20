package com.tum.ident;

import android.util.Log;

import com.tum.ident.identification.ConfigurationItem;
import com.tum.ident.storage.StorageHandler;


/**
 * The Class IdentificationConfiguration.
 */
public class IdentificationConfiguration {

	/** The hash length. */
	public static int hashLength = 64;

	/** The tag. */
	static String TAG = "IdentificationConfiguration";

	// StepDetector
	/** The dwt accept threshold. */
	public static double dwtAcceptThreshold = 8000;
	
	/** The dwt threshold. */
	public static double dwtThreshold = 9000;
	
	/** The dwt avg threshold. */
	public static double dwtAvgThreshold = 12000;
	
	/** The dwt embodied threshold. */
	public static double dwtEmbodiedThreshold = 8000;
	
	/** The acceleration array length. */
	public static int accelerationArrayLength = 150;

	/** The max step duration. */
	public static long maxStepDuration = 1000000000L; // average: 530973451
	
	/** The min step duration. */
	public static long minStepDuration = 180000000L;
	
	/** The diff step avg duration. */
	public static long diffStepAvgDuration = 100000000L;
	
	/** The pixel error area. */
	public static int pixelErrorArea = 3;

	
	// Spectrum
	/** The spectrum threshold. */
	public static double spectrumThreshold = 0.7;

	/** The config. */
	public static ConfigurationItem config = new ConfigurationItem();

	/**
	 * Inits the.
	 */
	public static void init() {
		load();
		save();
	}
	
	/**
	 * Load.
	 */
	public static void load() {
		if (config == null) {
			config = new ConfigurationItem();
		}
		ConfigurationItem newConfig = config;
		String fileName = "config.ser";
		config = (ConfigurationItem) StorageHandler.loadObject(fileName);
		Log.v(TAG, "config: " + config);
		if (config == null) {
			config = new ConfigurationItem();
		}
		Log.v(TAG, "+config.serverURL: " + config.serverURL);
		if (newConfig.serverURL.length() > 0) {
			config.serverURL = newConfig.serverURL;
		}
	}

	/**
	 * Save.
	 */
	public static void save() {
		String fileName = "config.ser";
		StorageHandler.saveObject(config, fileName);
	}

}
