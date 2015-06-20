package com.tum.ident.music;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



/**
 * The Class MusicItem.
 */
public class MusicItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The artist. */
	private String artist;
	
	/** The counter. */
	private long counter;

	/**
	 * Instantiates a new music item.
	 *
	 * @param artist the artist
	 */
	public MusicItem(String artist) {
		this.artist = artist;
		this.setCounter(1);
	}

	/**
	 * Gets the artist.
	 *
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Sets the artist.
	 *
	 * @param artist the new artist
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Gets the counter.
	 *
	 * @return the counter
	 */
	public long getCounter() {
		return counter;
	}

	/**
	 * Sets the counter.
	 *
	 * @param counter the new counter
	 */
	public void setCounter(long counter) {
		this.counter = counter;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
