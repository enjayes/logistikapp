package com.tum.ident.userdevice;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



/**
 * The Class PackageItem.
 */
public class PackageItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Install time. */
	private long InstallTime;
	
	/** The Update time. */
	private long UpdateTime;
	
	/** The app name. */
	private String appName;
	
	/** The package name. */
	private String packageName;
	
	/** The version name. */
	private String versionName;
	
	/** The id. */
	private long id;

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
	 * Gets the install time.
	 *
	 * @return the install time
	 */
	public long getInstallTime() {
		return InstallTime;
	}

	/**
	 * Gets the update time.
	 *
	 * @return the update time
	 */
	public long getUpdateTime() {
		return UpdateTime;
	}

	/**
	 * Gets the app name.
	 *
	 * @return the app name
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * Gets the package name.
	 *
	 * @return the package name
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * Gets the version name.
	 *
	 * @return the version name
	 */
	public String getVersionName() {
		return versionName;
	}

	/**
	 * Instantiates a new package item.
	 *
	 * @param appName the app name
	 * @param packageName the package name
	 * @param versionName the version name
	 * @param InstallTime the install time
	 * @param UpdateTime the update time
	 */
	public PackageItem(String appName, String packageName, String versionName,
			long InstallTime, long UpdateTime) {
		this.InstallTime = InstallTime;
		this.UpdateTime = UpdateTime;
		this.appName = appName;
		this.packageName = packageName;
		this.versionName = versionName;
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
