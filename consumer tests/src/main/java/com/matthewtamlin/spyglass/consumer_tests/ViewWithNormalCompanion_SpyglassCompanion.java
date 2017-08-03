package com.matthewtamlin.spyglass.consumer_tests;

import android.content.Context;
import android.content.res.TypedArray;

public class ViewWithNormalCompanion_SpyglassCompanion {
	private static boolean activateCallersWasInvoked = false;

	public static void activateCallers(
			final ViewWithNormalCompanion target,
			final Context context,
			final TypedArray attrs) {

		activateCallersWasInvoked = true;
	}

	public static boolean activateCallersWasInvoked() {
		return activateCallersWasInvoked;
	}
}