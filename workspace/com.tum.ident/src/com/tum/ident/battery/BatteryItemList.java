package com.tum.ident.battery;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import android.os.BatteryManager;



/**
 * The Class BatteryItemList.
 */
public class BatteryItemList implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The items. */
	private BatteryItem[][] items = new BatteryItem[7][48];
	
	/** The current day. */
	private int currentDay = -1;
	
	/** The current time slot. */
	private int currentTimeSlot = -1;
	
	/** The battery info set. */
	private boolean batteryInfoSet = false;
	
	/** The level. */
	private int level;

	/**
	 * Gets the minutes.
	 *
	 * @return the minutes
	 */
	static public int getMinutes() {
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		return hours * 60 + minutes;
	}

	/**
	 * Gets the day.
	 *
	 * @return the day
	 */
	static public int getDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * Update.
	 *
	 * @param level the level
	 * @param scale the scale
	 * @param plugged the plugged
	 * @param present the present
	 * @param status the status
	 * @param temperature the temperature
	 * @return true, if successful
	 */
	public boolean update(int level, int scale, int plugged, boolean present,
			int status, int temperature) {
		int day = getDay();
		int time = getMinutes();
		int timeslot = time / 30;
		if (timeslot != currentTimeSlot || day != currentDay) {
			if (batteryInfoSet) {
				int levelChange = level - this.level;
				this.level = level;
				boolean charging;
				if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
					charging = true;
				} else {
					charging = false;
				}
				if (items[day][timeslot] == null) {
					items[day][timeslot] = new BatteryItem();
				}
				items[day][timeslot].update(level, levelChange, temperature,
						plugged, present, charging);

				if (timeslot > 47) {
					timeslot = 47;
				}

			}
			batteryInfoSet = true;
			currentTimeSlot = timeslot;
			currentDay = day;
			return true;
		} else {
			return false;
		}

	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder
				.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.replaceAll("<null>,", "").replaceAll("\\{<null>\\},", "")
				.replaceAll("\\{<null>\\}", "").replaceAll(",<null>", "");
	}

}
