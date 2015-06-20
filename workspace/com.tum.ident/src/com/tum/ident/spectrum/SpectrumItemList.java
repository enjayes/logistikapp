package com.tum.ident.spectrum;

import java.io.Serializable;
import java.util.ArrayList;

import com.tum.ident.IdentificationConfiguration;



/**
 * The Class SpectrumItemList.
 */
public class SpectrumItemList implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The list. */
	private ArrayList<SpectrumItem> list = new ArrayList<SpectrumItem>();


	/**
	 * Adds the.
	 *
	 * @param spectrumItem the spectrum item
	 */
	public void add(SpectrumItem spectrumItem) {
		
		boolean spectrumFound = false;
		for (SpectrumItem sItem : list) {
			if(SpectrumItem.distance(spectrumItem, sItem)<IdentificationConfiguration.spectrumThreshold){
				sItem.merge(spectrumItem);
				spectrumFound = true;
				break;
			}
		}
		
		if (spectrumFound) {
			
		} else {
			list.add(spectrumItem);
		}
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public ArrayList<SpectrumItem> getList(){
		return list;
	}
	
	
}
