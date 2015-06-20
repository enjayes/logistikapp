package com.tum.ident.files;

import java.io.Serializable;
import java.util.ArrayList;

import com.tum.ident.util.Util;



/**
 * The Class FileItemList.
 */
public class FileItemList implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The list. */
	private ArrayList<FileItem> list = new ArrayList<FileItem>();

	/**
	 * Adds the.
	 *
	 * @param item the item
	 */
	public void add(FileItem item) {
		list.add(item);
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public ArrayList<FileItem> getList() {
		return list;
	}

	/**
	 * Sets the list.
	 *
	 * @param list the new list
	 */
	public void setList(ArrayList<FileItem> list) {
		this.list = list;
	}

	/**
	 * Gets the file string.
	 *
	 * @return the file string
	 */
	public String getFileString() {
		return Util.toStringFilterNewLine(list);
	}

}
