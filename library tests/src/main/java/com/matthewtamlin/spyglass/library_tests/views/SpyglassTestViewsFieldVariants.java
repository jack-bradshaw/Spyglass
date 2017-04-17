package com.matthewtamlin.spyglass.library_tests.views;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.library.value_handler_annotations.StringHandler;

import static com.matthewtamlin.spyglass.library_tests.R.styleable.SpyglassTestView_string_attr;

public class SpyglassTestViewsFieldVariants {
	public static final String DEFAULT_STRING = "default string";

	public static class NoAnnotations extends SpyglassTestView {
		public String spyglassField;

		public NoAnnotations(final Context context) {
			super(context);
		}
	}

	public static class OptionalStringHandlerNoDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr, mandatory = false)
		public String spyglassField;

		public OptionalStringHandlerNoDefault(final Context context) {
			super(context);
		}
	}

	public static class OptionalStringHandlerWithDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr, mandatory = false)
		@DefaultToString(DEFAULT_STRING)
		public String spyglassField;

		public OptionalStringHandlerWithDefault(final Context context) {
			super(context);
		}
	}

	public static class MandatoryStringHandlerNoDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr, mandatory = true)
		public String spyglassField;

		public MandatoryStringHandlerNoDefault(final Context context) {
			super(context);
		}
	}

	public static class MandatoryStringHandlerWithDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr, mandatory = true)
		@DefaultToString(DEFAULT_STRING)
		public String spyglassField;

		public MandatoryStringHandlerWithDefault(final Context context) {
			super(context);
		}
	}

	public static class HandlerTypeMismatch extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		public boolean spyglassField;

		public HandlerTypeMismatch(final Context context) {
			super(context);
		}
	}

	public static class DefaultTypeMismatch extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		@DefaultToBoolean(false)
		public String spyglassField;

		public DefaultTypeMismatch(final Context context) {
			super(context);
		}
	}
}