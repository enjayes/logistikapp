package com.tum.ident.camera;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * The Class CameraPixelList.
 */
public class CameraPixelList implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The index. */
	private int index = 0;
	
	/** The list. */
	private ArrayList<CameraPixel> list = new ArrayList<CameraPixel>();
	
	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Sets the index.
	 *
	 * @param index the new index
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public ArrayList<CameraPixel> getList() {
		return list;
	}
	
	/**
	 * Sets the list.
	 *
	 * @param list the new list
	 */
	public void setList(ArrayList<CameraPixel> list) {
		this.list = list;
	}
	
	
}
