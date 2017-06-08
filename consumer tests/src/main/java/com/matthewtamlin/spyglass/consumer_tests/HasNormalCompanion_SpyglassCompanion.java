package com.matthewtamlin.spyglass.consumer_tests;

public class HasNormalCompanion_SpyglassCompanion {
	private static boolean activateCallersWasInvoked = false;

	public static void activateCallers() {
		activateCallersWasInvoked = true;
	}

	public static boolean activateCallersWasInvoked() {
		return activateCallersWasInvoked;
	}
}