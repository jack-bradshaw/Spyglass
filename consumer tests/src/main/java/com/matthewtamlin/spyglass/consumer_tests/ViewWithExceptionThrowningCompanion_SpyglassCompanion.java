package com.matthewtamlin.spyglass.consumer_tests;

import android.content.Context;
import android.content.res.TypedArray;

public class ViewWithExceptionThrowningCompanion_SpyglassCompanion {
	public void activateCallers(
			final ViewWithExceptionThrowingCompanion target,
			final Context context,
			final TypedArray attrs) {

		throw new IllegalArgumentException();
	}
}