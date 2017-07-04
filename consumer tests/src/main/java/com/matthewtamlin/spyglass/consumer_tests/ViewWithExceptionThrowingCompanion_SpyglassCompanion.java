package com.matthewtamlin.spyglass.consumer_tests;

import android.content.Context;
import android.content.res.TypedArray;

public class ViewWithExceptionThrowingCompanion_SpyglassCompanion {
	public static void activateCallers(
			final ViewWithTargetExceptionThrowingCompanion target,
			final Context context,
			final TypedArray attrs) {

		throw new IllegalArgumentException();
	}
}