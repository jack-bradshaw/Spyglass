package com.matthewtamlin.spyglass.consumer_tests;

import android.content.Context;
import android.content.res.TypedArray;

public class ViewWithCompanionThrowsTargetException_SpyglassCompanion {
	public static void activateCallers(
			final ViewWithCompanionThrowsTargetException target,
			final Context context,
			final TypedArray attrs) {

		throw new IllegalArgumentException();
	}
}