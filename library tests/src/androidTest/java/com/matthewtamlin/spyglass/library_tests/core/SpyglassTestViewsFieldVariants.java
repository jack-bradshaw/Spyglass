package com.matthewtamlin.spyglass.library_tests.core;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.library.value_handler_annotations.StringHandler;

import static com.matthewtamlin.spyglass.library_tests.R.styleable.SpyglassTestView_string_attr;
import static com.matthewtamlin.spyglass.library_tests.core.Constants.DEFAULT_STRING;
import static com.matthewtamlin.spyglass.library_tests.core.Constants.INITIAL_STRING;

public class SpyglassTestViewsFieldVariants {
	public static class NoAnnotations extends SpyglassTestView {
		public String spyglassField = INITIAL_STRING;

		public NoAnnotations(final Context context) {
			super(context);
		}
	}

	public static class OnlyHandlerPresent extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		public String spyglassField = INITIAL_STRING;

		public OnlyHandlerPresent(final Context context) {
			super(context);
		}
	}

	public static class HandlerAndDefaultPresent extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		@DefaultToString(DEFAULT_STRING)
		public String spyglassField = INITIAL_STRING;

		public HandlerAndDefaultPresent(final Context context) {
			super(context);
		}
	}

	public static class HandlerTypeMismatch extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		public boolean spyglassField = false;

		public HandlerTypeMismatch(final Context context) {
			super(context);
		}
	}

	public static class DefaultTypeMismatch extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		@DefaultToBoolean(false)
		public String spyglassField = INITIAL_STRING;

		public DefaultTypeMismatch(final Context context) {
			super(context);
		}
	}
}