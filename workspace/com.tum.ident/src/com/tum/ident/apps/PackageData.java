package com.tum.ident.apps;

import java.util.ArrayList;
import java.util.List;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.tum.ident.userdevice.PackageItem;
import com.tum.ident.util.HashGenerator;



/**
 * The Class PackageData.
 */
public class PackageData {

	/**
	 * Gets the package data.
	 *
	 * @param context the context
	 * @param systemPackages the system packages
	 * @return the package data
	 */
	public static ArrayList<PackageItem> getPackageData(Context context,
			boolean systemPackages) {
		if (systemPackages) {
			Log.v("DEBUG", "Search for System-Packages! ");
		}
		
		/*
		List<PackageInfo> installedPackages = context.getPackageManager()
				.getInstalledPackages(0);
		
		
		for (int i = 0; i < installedPackages.size(); i++) {
			PackageInfo p = installedPackages.get(i);
			[...]
			ApplicationInfo ai = context.getPackageManager()
			.getApplicationInfo(p.packageName, 0);
			if ((ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				systemApp = true;
			}
			else{
				systemApp = false;
			}
			[...]
		}
		*/
		
		ArrayList<PackageItem> packageList = new ArrayList<PackageItem>();
		List<PackageInfo> installedPackages = context.getPackageManager()
				.getInstalledPackages(0);
		int numPackages = installedPackages.size();
		for (int i = 0; i < numPackages; i++) {
			PackageInfo p = installedPackages.get(i);
			if (p.versionName == null) {
				continue;
			}
			long InstallTime = p.firstInstallTime;
			long UpdateTime = p.lastUpdateTime;
			String appname = p.applicationInfo.loadLabel(
					context.getPackageManager()).toString();
			String pname = p.packageName;
			String versionName = p.versionName;
			boolean systemApp = false;
			try {
				ApplicationInfo ai = context.getPackageManager()
						.getApplicationInfo(p.packageName, 0);
				if ((ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					systemApp = true;
				}

			} catch (NameNotFoundException e) {
				systemApp = false;
			}
			if (systemApp == systemPackages) {
				if (systemApp) {
					Log.v("DEBUG", "System-Package: " + appname);
				}
				packageList.add(new PackageItem(HashGenerator.hash(appname),
						HashGenerator.hash(pname), HashGenerator
								.hash(versionName), InstallTime, UpdateTime));
			}
		}
		Log.v("UserData", "packageList.size:" + packageList.size());

		return packageList;
	}

	/**
	 * Gets the running app.
	 *
	 * @param context the context
	 * @return the running app
	 */
	public static String getRunningApp(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		List<RunningTaskInfo> taskList = am.getRunningTasks(1);
		if (taskList.size() > 0) {
			ComponentName p = taskList.get(0).topActivity;
			if (p != null) {
				return HashGenerator.hash(p.getPackageName());
			}
		}
		return "";
	}
}
