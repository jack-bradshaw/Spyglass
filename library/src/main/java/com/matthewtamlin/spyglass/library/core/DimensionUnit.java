package com.matthewtamlin.spyglass.library.core;

import android.content.Context;

import com.matthewtamlin.android_utilities.library.helpers.DimensionHelper;

public enum DimensionUnit {
	PX {
		@Override
		public int convertToPx(final Context context, final int value) {
			return value;
		}
	},

	DP {
		@Override
		public int convertToPx(final Context context, final int value) {
			return DimensionHelper.dpToPx(context, value);
		}
	},

	PT{
		@Override
		public int convertToPx(final Context context, final int value) {
			return (int) DimensionHelper.ptToPx(context, value);
		}
	},

	IN{
		@Override
		public int convertToPx(final Context context, final int value) {
			return (int) DimensionHelper.inToPx(context, value);
		}
	},

	SP{
		@Override
		public int convertToPx(final Context context, final int value) {
			return (int) DimensionHelper.spToPx(context, value);
		}
	},

	MM{
		@Override
		public int convertToPx(final Context context, final int value) {
			return (int) DimensionHelper.mmToPx(context, value);
		}
	};

	public abstract int convertToPx(final Context context, final int value);
}