package com.tum.ident.user;

import java.io.Serializable;


/**
 * The Class UserMatch.
 */
public class UserMatch implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	transient private long id;

	/** The gid. */
	private String gid;

	/** The bluetooth match. */
	private double bluetoothMatch = 0;
	
	/** The bluetooth significance. */
	private double bluetoothSignificance = 0;

	/** The wlan match. */
	private double wlanMatch = 0;
	
	/** The wlan significance. */
	private double wlanSignificance = 0;

	/** The package match. */
	private double packageMatch = 0;
	
	/** The package significance. */
	private double packageSignificance = 0;

	/** The contact match. */
	private double contactMatch = 0;
	
	/** The contact significance. */
	private double contactSignificance = 0;

	/** The account match. */
	private double accountMatch = 0;
	
	/** The account significance. */
	private double accountSignificance = 0;

	/** The call log match. */
	private double callLogMatch = 0;
	
	/** The call log significance. */
	private double callLogSignificance = 0;


	/** The gait match. */
	private double gaitMatch = -1;
	
	/** The spectrum match. */
	private double spectrumMatch = -1;
	
	/** The music match. */
	private double musicMatch       = -1;
	
	/** The files match. */
	private double filesMatch       = -1;
	
	/** The orientation match. */
	private double orientationMatch = -1;
	
	/** The battery match. */
	private double batteryMatch     = -1;
	
	/** The locations match. */
	private double locationsMatch   = -1;

	

	
	/** The user name match. */
	private double userNameMatch;

	/** The match. */
	private double match;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the match.
	 *
	 * @return the match
	 */
	public double getMatch() {
		return match;
	}

	/**
	 * Sets the match.
	 *
	 * @param match the new match
	 */
	public void setMatch(double match) {
		this.match = match;
	}
	
	
	
	
	
	/**
	 * Gets the spectrum match.
	 *
	 * @return the spectrum match
	 */
	public double getSpectrumMatch() {
		return spectrumMatch;
	}

	/**
	 * Sets the spectrum match.
	 *
	 * @param spectrumMatch the new spectrum match
	 */
	public void setSpectrumMatch(double spectrumMatch) {
		this.spectrumMatch = spectrumMatch;
	}

	/**
	 * Gets the music match.
	 *
	 * @return the music match
	 */
	public double getMusicMatch() {
		return musicMatch;
	}

	/**
	 * Sets the music match.
	 *
	 * @param musicMatch the new music match
	 */
	public void setMusicMatch(double musicMatch) {
		this.musicMatch = musicMatch;
	}

	/**
	 * Gets the orientation match.
	 *
	 * @return the orientation match
	 */
	public double getOrientationMatch() {
		return orientationMatch;
	}

	/**
	 * Sets the orientation match.
	 *
	 * @param orientationMatch the new orientation match
	 */
	public void setOrientationMatch(double orientationMatch) {
		this.orientationMatch = orientationMatch;
	}

	/**
	 * Gets the locations match.
	 *
	 * @return the locations match
	 */
	public double getLocationsMatch() {
		return locationsMatch;
	}

	/**
	 * Sets the locations match.
	 *
	 * @param locationsMatch the new locations match
	 */
	public void setLocationsMatch(double locationsMatch) {
		this.locationsMatch = locationsMatch;
	}

	/**
	 * Sets the gait match.
	 *
	 * @param gaitMatch the new gait match
	 */
	public void setGaitMatch(double gaitMatch) {
		this.gaitMatch = gaitMatch;
	}

	/**
	 * Gets the gait match.
	 *
	 * @return the gait match
	 */
	public double getGaitMatch() {
		return gaitMatch;
	}

	/**
	 * Gets the bluetooth match.
	 *
	 * @return the bluetooth match
	 */
	public double getBluetoothMatch() {
		return bluetoothMatch;
	}

	/**
	 * Sets the bluetooth match.
	 *
	 * @param bluetoothMatch the new bluetooth match
	 */
	public void setBluetoothMatch(double bluetoothMatch) {
		this.bluetoothMatch = bluetoothMatch;
	}

	/**
	 * Gets the bluetooth significance.
	 *
	 * @return the bluetooth significance
	 */
	public double getBluetoothSignificance() {
		return bluetoothSignificance;
	}

	/**
	 * Sets the bluetooth significance.
	 *
	 * @param bluetoothSignificance the new bluetooth significance
	 */
	public void setBluetoothSignificance(double bluetoothSignificance) {
		this.bluetoothSignificance = bluetoothSignificance;
	}

	/**
	 * Gets the wlan match.
	 *
	 * @return the wlan match
	 */
	public double getWlanMatch() {
		return wlanMatch;
	}

	/**
	 * Sets the wlan match.
	 *
	 * @param wlanMatch the new wlan match
	 */
	public void setWlanMatch(double wlanMatch) {
		this.wlanMatch = wlanMatch;
	}

	/**
	 * Gets the wlan significance.
	 *
	 * @return the wlan significance
	 */
	public double getWlanSignificance() {
		return wlanSignificance;
	}

	/**
	 * Sets the wlan significance.
	 *
	 * @param wlanSignificance the new wlan significance
	 */
	public void setWlanSignificance(double wlanSignificance) {
		this.wlanSignificance = wlanSignificance;
	}

	/**
	 * Gets the package match.
	 *
	 * @return the package match
	 */
	public double getPackageMatch() {
		return packageMatch;
	}

	/**
	 * Sets the package match.
	 *
	 * @param packageMatch the new package match
	 */
	public void setPackageMatch(double packageMatch) {
		this.packageMatch = packageMatch;
	}

	/**
	 * Gets the package significance.
	 *
	 * @return the package significance
	 */
	public double getPackageSignificance() {
		return packageSignificance;
	}

	/**
	 * Sets the package significance.
	 *
	 * @param packageSignificance the new package significance
	 */
	public void setPackageSignificance(double packageSignificance) {
		this.packageSignificance = packageSignificance;
	}

	/**
	 * Gets the contact match.
	 *
	 * @return the contact match
	 */
	public double getContactMatch() {
		return contactMatch;
	}

	/**
	 * Sets the contact match.
	 *
	 * @param contactMatch the new contact match
	 */
	public void setContactMatch(double contactMatch) {
		this.contactMatch = contactMatch;
	}

	/**
	 * Gets the contact significance.
	 *
	 * @return the contact significance
	 */
	public double getContactSignificance() {
		return contactSignificance;
	}

	/**
	 * Sets the contact significance.
	 *
	 * @param contactSignificance the new contact significance
	 */
	public void setContactSignificance(double contactSignificance) {
		this.contactSignificance = contactSignificance;
	}

	/**
	 * Gets the account match.
	 *
	 * @return the account match
	 */
	public double getAccountMatch() {
		return accountMatch;
	}

	/**
	 * Sets the account match.
	 *
	 * @param accountMatch the new account match
	 */
	public void setAccountMatch(double accountMatch) {
		this.accountMatch = accountMatch;
	}

	/**
	 * Gets the account significance.
	 *
	 * @return the account significance
	 */
	public double getAccountSignificance() {
		return accountSignificance;
	}

	/**
	 * Sets the account significance.
	 *
	 * @param accountSignificance the new account significance
	 */
	public void setAccountSignificance(double accountSignificance) {
		this.accountSignificance = accountSignificance;
	}

	/**
	 * Gets the call log match.
	 *
	 * @return the call log match
	 */
	public double getCallLogMatch() {
		return callLogMatch;
	}

	/**
	 * Sets the call log match.
	 *
	 * @param callLogMatch the new call log match
	 */
	public void setCallLogMatch(double callLogMatch) {
		this.callLogMatch = callLogMatch;
	}

	/**
	 * Gets the call log significance.
	 *
	 * @return the call log significance
	 */
	public double getCallLogSignificance() {
		return callLogSignificance;
	}

	/**
	 * Sets the call log significance.
	 *
	 * @param callLogSignificance the new call log significance
	 */
	public void setCallLogSignificance(double callLogSignificance) {
		this.callLogSignificance = callLogSignificance;
	}

	/**
	 * Gets the user name match.
	 *
	 * @return the user name match
	 */
	public double getUserNameMatch() {
		return userNameMatch;
	}

	/**
	 * Sets the user name match.
	 *
	 * @param userNameMatch the new user name match
	 */
	public void setUserNameMatch(double userNameMatch) {
		this.userNameMatch = userNameMatch;
	}

	/**
	 * Gets the gid.
	 *
	 * @return the gid
	 */
	public String getGid() {
		return gid;
	}

	/**
	 * Sets the gid.
	 *
	 * @param gid the new gid
	 */
	public void setGid(String gid) {
		this.gid = gid;
	}

}
