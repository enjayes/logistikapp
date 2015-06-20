package com.tum.ident.device;

import java.io.Serializable;


/**
 * The Class DeviceMatch.
 */
public class DeviceMatch implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	private long id;
	
	/** The gid. */
	private String gid;

	/** The match. */
	private double match = 0;
	
	/** The properties. */
	private double[] properties = null;

	/** The dark frame match. */
	private double darkFrameMatch = -1;
	
	/** The pixel match. */
	private double pixelMatch = -1;
	
	
	/**
	 * Gets the dark frame match.
	 *
	 * @return the dark frame match
	 */
	public double getDarkFrameMatch() {
		return darkFrameMatch;
	}

	/**
	 * Sets the dark frame match.
	 *
	 * @param darkFrameMatch the new dark frame match
	 */
	public void setDarkFrameMatch(double darkFrameMatch) {
		this.darkFrameMatch = darkFrameMatch;
	}

	/**
	 * Gets the pixel match.
	 *
	 * @return the pixel match
	 */
	public double getPixelMatch() {
		return pixelMatch;
	}

	/**
	 * Sets the pixel match.
	 *
	 * @param pixelMatch the new pixel match
	 */
	public void setPixelMatch(double pixelMatch) {
		this.pixelMatch = pixelMatch;
	}
	
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
	 * Sets the match.
	 *
	 * @param match the new match
	 */
	public void setMatch(double match) {
		this.match = Math.max(match, this.match);
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
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public double[] getProperties() {
		return properties;
	}

	/**
	 * Sets the properties.
	 *
	 * @param properties the new properties
	 */
	public void setProperties(double[] properties) {
		this.properties = properties;
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
