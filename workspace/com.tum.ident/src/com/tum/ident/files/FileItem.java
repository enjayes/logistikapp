package com.tum.ident.files;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



/**
 * The Class FileItem.
 */
public class FileItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The hash. */
	private String hash;
	
	/** The folder type. */
	private String folderType;

	/**
	 * Instantiates a new file item.
	 *
	 * @param hash the hash
	 * @param folderType the folder type
	 */
	public FileItem(String hash, String folderType) {
		this.hash = hash;
		this.folderType = folderType;
	}

	/**
	 * Gets the file hash.
	 *
	 * @return the file hash
	 */
	public String getFileHash() {
		return hash;
	}

	/**
	 * Sets the file hash.
	 *
	 * @param hash the new file hash
	 */
	public void setFileHash(String hash) {
		this.hash = hash;
	}

	/**
	 * Gets the folder type.
	 *
	 * @return the folder type
	 */
	public String getFolderType() {
		return folderType;
	}

	/**
	 * Sets the folder type.
	 *
	 * @param folderType the new folder type
	 */
	public void setFolderType(String folderType) {
		this.folderType = folderType;
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
