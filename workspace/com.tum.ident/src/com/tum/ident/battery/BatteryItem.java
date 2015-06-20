package com.tum.ident.battery;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import android.os.BatteryManager;




/**
 * The Class BatteryItem.
 */
public class BatteryItem implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The level. */
	private double level;
	
	/** The level change. */
	private double levelChange;
	
	/** The plugged ac. */
	private double pluggedAC;
	
	/** The plugged usb. */
	private double pluggedUSB;
	
	/** The plugged wireless. */
	private double pluggedWireless;
	
	/** The present. */
	private double present;
	
	/** The charging. */
	private double charging;
	
	/** The temperature. */
	private double temperature;
	
	/** The level weight. */
	private int levelWeight = 0;
	
	/** The charging weight. */
	private int chargingWeight = 0;
	
	/** The present weight. */
	private int presentWeight = 0;
	
	/** The plugged weight. */
	private int pluggedWeight = 0;

	/**
	 * Instantiates a new battery item.
	 */
	public BatteryItem() {
		levelWeight = 0;
		chargingWeight = 0;
		presentWeight = 0;
	}

	/**
	 * Update.
	 *
	 * @param level the level
	 * @param levelChange the level change
	 * @param temperature the temperature of the battery
	 * @param plugged describes if the device is plugged in
	 * @param present describes if the battery is present
	 * @param charging the charging
	 */
	public void update(int level, int levelChange, int temperature,
			int plugged, boolean present, boolean charging) {
		
		if(plugged != 0){
			if(plugged ==  BatteryManager.BATTERY_PLUGGED_AC ){
				this.pluggedAC = (this.pluggedAC * pluggedWeight +1.0)
						/ (pluggedWeight + 1.0);
			}
			else{
				this.pluggedAC = (this.pluggedAC * pluggedWeight)
						/ (pluggedWeight + 1.0);
			}
			if(plugged ==  BatteryManager.BATTERY_PLUGGED_USB ){
				this.pluggedUSB = (this.pluggedUSB * pluggedWeight +1.0)
						/ (pluggedWeight + 1.0);
			}
			else{
				this.pluggedUSB = (this.pluggedUSB * pluggedWeight)
						/ (pluggedWeight + 1.0);
			}
			if(plugged ==  BatteryManager.BATTERY_PLUGGED_WIRELESS ){
				this.pluggedWireless = (this.pluggedWireless * pluggedWeight +1.0)
						/ (pluggedWeight + 1.0);
			}
			else {
				this.pluggedWireless = (this.pluggedWireless * pluggedWeight)
						/ (pluggedWeight + 1.0);
			}
			pluggedWeight++;
		}
		
		if (present == false) {
			this.present = (this.present * presentWeight)
					/ (presentWeight + 1.0);
		} else {
			this.present = (this.present * presentWeight + 1.0)
					/ (presentWeight + 1.0);
			this.temperature = (this.temperature * chargingWeight + temperature)
					/ (chargingWeight + 1.0);
			if (charging) {
				this.charging = (this.charging * chargingWeight + 1.0)
						/ (chargingWeight + 1.0);
			} else {
				this.charging = (this.charging * chargingWeight)
						/ (chargingWeight + 1.0);
				this.level = (this.level * levelWeight + levelWeight)
						/ (levelWeight + 1.0);
				this.levelChange = (this.levelChange * levelWeight + levelChange)
						/ (levelWeight + 1.0);
				levelWeight++;
			}
			chargingWeight++;
		}
		presentWeight++;
	}
	
	
	

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public double getLevel() {
		return level;
	}

	/**
	 * Gets the level change.
	 *
	 * @return the level change
	 */
	public double getLevelChange() {
		return levelChange;
	}

	/**
	 * Gets the plugged ac.
	 *
	 * @return the plugged ac
	 */
	public double getPluggedAC() {
		return pluggedAC;
	}

	/**
	 * Gets the plugged usb.
	 *
	 * @return the plugged usb
	 */
	public double getPluggedUSB() {
		return pluggedUSB;
	}

	/**
	 * Gets the plugged wireless.
	 *
	 * @return the plugged wireless
	 */
	public double getPluggedWireless() {
		return pluggedWireless;
	}

	/**
	 * Gets the present.
	 *
	 * @return the present
	 */
	public double getPresent() {
		return present;
	}

	/**
	 * Gets the charging.
	 *
	 * @return the charging
	 */
	public double getCharging() {
		return charging;
	}

	/**
	 * Gets the temperature.
	 *
	 * @return the temperature
	 */
	public double getTemperature() {
		return temperature;
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
