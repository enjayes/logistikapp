package com.tum.ident.music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.tum.ident.util.Util;



/**
 * The Class MusicItemList.
 */
public class MusicItemList implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The list. */
	private ArrayList<MusicItem> list = new ArrayList<MusicItem>();

	/**
	 * Adds the.
	 *
	 * @param item the item
	 */
	public void add(MusicItem item) {
		list.add(item);
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public ArrayList<MusicItem> getList() {
		return list;
	}

	/**
	 * Sets the list.
	 *
	 * @param list the new list
	 */
	public void setList(ArrayList<MusicItem> list) {
		this.list = list;
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Gets the music string.
	 *
	 * @return the music string
	 */
	public String getMusicString() {
		return Util.toStringFilterNewLine(list);
	}

	/**
	 * Sort.
	 */
	public void sort() {
		// Sorting
		Collections.sort(list, new Comparator<MusicItem>() {
			@Override
			public int compare(MusicItem item1, MusicItem item2) {
				int sgn = (int) Math.signum(item2.getCounter()
						- item1.getCounter());
				return sgn;
			}
		});
	}

}
