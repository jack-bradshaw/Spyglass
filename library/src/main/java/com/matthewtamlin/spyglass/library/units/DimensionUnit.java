package com.matthewtamlin.spyglass.library.units;

import android.content.Context;

import com.matthewtamlin.android_utilities.library.helpers.DimensionHelper;

/**
 * A unit of measurement which can quantify a screen dimensions.
 */
public enum DimensionUnit {
	/**
	 * Pixels. The actual pixel count on the screen.
	 */
	PX {
		@Override
		public float convertToPx(final Context context, final float value) {
			return value;
		}
	},

	/**
	 * Display independent pixels. Automatically scaled by the system based on screen density.
	 */
	DP {
		@Override
		public float convertToPx(final Context context, final float value) {
			return DimensionHelper.dpToPx(context, value);
		}
	},

	/**
	 * Points. Equal to 1/72 of an inch.
	 */
	PT {
		@Override
		public float convertToPx(final Context context, final float value) {
			return DimensionHelper.ptToPx(context, value);
		}
	},

	/**
	 * Inches. Actual distance on the screen.
	 */
	IN {
		@Override
		public float convertToPx(final Context context, final float value) {
			return DimensionHelper.inToPx(context, value);
		}
	},

	/**
	 * Scale independent pixels. Automatically scaled by the system based on the user's font size
	 * preference.
	 */
	SP {
		@Override
		public float convertToPx(final Context context, final float value) {
			return DimensionHelper.spToPx(context, value);
		}
	},

	/**
	 * Millimetres. Actual distance on the screen.
	 */
	MM {
		@Override
		public float convertToPx(final Context context, final float value) {
			return DimensionHelper.mmToPx(context, value);
		}
	};

	/**
	 * Converts a value from this unit to pixels.
	 *
	 * @param context
	 * 		a context which provides access to system resources, not null
	 * @param value
	 * 		the value to convert, measured in this unit
	 *
	 * @return the supplied value, converted to units of pixels
	 */
	public abstract float convertToPx(final Context context, final float value);
}