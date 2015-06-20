package com.tum.ident.camera;

import java.io.Serializable;


/**
 * The Class CameraPixel.
 */
public class CameraPixel implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Enum Type.
	 */
	enum Type {
		
		/** The Hot pixel. */
		HotPixel, 
 /** The Dead pixel. */
 DeadPixel
	}

	/** The x. */
	private int x;
	
	/** The y. */
	private int y;
	
	/** The color. */
	private int color;
	
	/** The type. */
	private Type type;
	
	/** The counter. */
	private int counter = 0;

	/**
	 * Instantiates a new camera pixel.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	public CameraPixel(int x, int y, int color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.counter = 0;
	}
	
	/**
	 * Increment counter.
	 */
	public void incrementCounter(){
		counter++;
	}
	
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Gets the counter.
	 *
	 * @return the counter
	 */
	public int getCounter() {
		return counter;
	}

}
