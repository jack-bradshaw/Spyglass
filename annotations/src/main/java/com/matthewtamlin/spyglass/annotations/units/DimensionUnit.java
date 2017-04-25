package com.matthewtamlin.spyglass.annotations.units;

/**
 * A unit of measurement which can quantify a screen dimensions.
 */
public enum DimensionUnit {
	/**
	 * Pixels. The actual pixel count on the screen.
	 */
	PX,

	/**
	 * Display independent pixels. Automatically scaled by the system based on screen density.
	 */
	DP,

	/**
	 * Points. Equal to 1/72 of an inch.
	 */
	PT,

	/**
	 * Inches. Actual distance on the screen.
	 */
	IN,

	/**
	 * Scale independent pixels. Automatically scaled by the system based on the user's font size
	 * preference.
	 */
	SP,

	/**
	 * Millimetres. Actual distance on the screen.
	 */
	MM
}