package com.matthewtamlin.spyglass.library.core;

import android.content.Context;

import com.matthewtamlin.android_utilities.library.helpers.DimensionHelper;

public enum DimensionUnit {
	PX {
		@Override
		public float convertToPx(final Context context, final float value) {
			return value;
		}
	},

	DP {
		@Override
		public float convertToPx(final Context context, final float value) {
			return DimensionHelper.dpToPx(context, value);
		}
	},

	PT {
		@Override
		public float convertToPx(final Context context, final float value) {
			return DimensionHelper.ptToPx(context, value);
		}
	},

	IN {
		@Override
		public float convertToPx(final Context context, final float value) {
			return DimensionHelper.inToPx(context, value);
		}
	},

	SP {
		@Override
		public float convertToPx(final Context context, final float value) {
			return DimensionHelper.spToPx(context, value);
		}
	},

	MM {
		@Override
		public float convertToPx(final Context context, final float value) {
			return DimensionHelper.mmToPx(context, value);
		}
	};

	public abstract float convertToPx(final Context context, final float value);
}