package com.matthewtamlin.spyglass.consumer_tests;

import android.content.Context;
import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.common.exception.SpyglassRuntimeException;

public class ViewWithCompanionThrowsSpyglassRuntimeException_SpyglassCompanion {
	public static void activateCallers(
			final ViewWithCompanionThrowsSpyglassRuntimeException	 target,
			final Context context,
			final TypedArray attrs) {

		throw new SpyglassRuntimeException();
	}
}