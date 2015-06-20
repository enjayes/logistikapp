package com.tum.ident.camera;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import android.graphics.Bitmap;


/**
 * The Class DarkFrame.
 */
public class DarkFrame implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The image. */
	transient private Bitmap image = null;
	
	/** The data. */
	private byte[] data;
	
	/** The index. */
	private int index;
	
	/** The width. */
	private int width = 400;
	
	/** The height. */
	private int height = 400;
	
	/** The weight. */
	private long weight = 0;
	
	/** The red. */
	private double red = 0;
	
	/** The green. */
	private double green = 0;
	
	/** The blue. */
	private double blue = 0;
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public Bitmap getImage() {
		return image;
	}
	
	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}
	
	/**
	 * Creates the data.
	 */
	public void createData(){
		if(image!=null){
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100,
					bytes);
			this.data = bytes.toByteArray();
			try {
				bytes.close();
			} catch (IOException e) {
			
			}
		}
	}
	

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}
	
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
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public long getWeight() {
		return weight;
	}
	
	
	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(long weight) {
		this.weight = weight;
	}
	
	/**
	 * Gets the red.
	 *
	 * @return the red
	 */
	public double getRed() {
		return red;
	}
	
	/**
	 * Sets the red.
	 *
	 * @param red the new red
	 */
	public void setRed(double red) {
		this.red = red;
	}
	
	/**
	 * Gets the green.
	 *
	 * @return the green
	 */
	public double getGreen() {
		return green;
	}
	
	/**
	 * Sets the green.
	 *
	 * @param green the new green
	 */
	public void setGreen(double green) {
		this.green = green;
	}
	
	/**
	 * Gets the blue.
	 *
	 * @return the blue
	 */
	public double getBlue() {
		return blue;
	}
	
	/**
	 * Sets the blue.
	 *
	 * @param blue the new blue
	 */
	public void setBlue(double blue) {
		this.blue = blue;
	}
	
	
	/**
	 * Adds the red.
	 *
	 * @param r the r
	 */
	public void addRed(double r){
		red = ((red * weight) + r)/ (weight + 1);
	}
	
	/**
	 * Adds the green.
	 *
	 * @param g the g
	 */
	public void addGreen(double g){
		green = ((green * weight) + g )/ (weight + 1);
	}
	
	/**
	 * Adds the blue.
	 *
	 * @param b the b
	 */
	public void addBlue(double b){
		blue = ((blue * weight) + b)/ (weight + 1);
	}
	
	/**
	 * Increase weight.
	 */
	public void increaseWeight() {
		this.weight++;
	}
	
}
