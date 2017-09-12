package com.matthewtamlin.spyglass.consumer_tests;

import android.content.Context;
import android.view.View;

public class NestedContainer {
	public static class NestedClass extends View {
		public NestedClass(final Context context) {
			super(context);
		}
	}
}