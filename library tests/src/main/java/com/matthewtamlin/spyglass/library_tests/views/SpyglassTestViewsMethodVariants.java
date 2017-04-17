package com.matthewtamlin.spyglass.library_tests.views;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.library.use_annotations.UseByte;
import com.matthewtamlin.spyglass.library.value_handler_annotations.StringHandler;

import static com.matthewtamlin.spyglass.library_tests.R.styleable.SpyglassTestView_string_attr;
import static com.matthewtamlin.spyglass.library_tests.views.SpyglassTestViewsMethodVariants.USE_BYTE_VALUE;

public class SpyglassTestViewsMethodVariants {
	public static final byte USE_BYTE_VALUE = 54;

	public static final String DEFAULT_STRING = "default string";

	public static class BaseClass extends SpyglassTestView {
		private Object[] argsFromLastSpyglassMethodInvocation;

		public BaseClass(final Context context) {
			super(context);
		}

		public Object[] getArgsFromLastSpyglassMethodInvocation() {
			return argsFromLastSpyglassMethodInvocation;
		}

		public void setArgsFromLastSpyglassMethodInvocation(final Object[] args) {
			this.argsFromLastSpyglassMethodInvocation = args;
		}
	}

	public static class NoAnnotations extends SpyglassTestView {
		public void spyglassMethod(final String arg1, byte arg2) {}

		public NoAnnotations(final Context context) {
			super(context);
		}
	}

	public static class OptionalStringHandlerNoDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr, mandatory = false)
		public void spyglassMethod(final String arg1, @UseByte(USE_BYTE_VALUE) byte arg2) {}

		public OptionalStringHandlerNoDefault(final Context context) {
			super(context);
		}
	}

	public static class OptionalStringHandlerWithDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr, mandatory = false)
		@DefaultToString(DEFAULT_STRING)
		public void spyglassMethod(final String arg1, @UseByte(USE_BYTE_VALUE) byte arg2) {}

		public OptionalStringHandlerWithDefault(final Context context) {
			super(context);
		}
	}

	public static class MandatoryStringHandlerNoDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr, mandatory = true)
		public void spyglassMethod(final String arg1, @UseByte(USE_BYTE_VALUE) byte arg2) {}

		public MandatoryStringHandlerNoDefault(final Context context) {
			super(context);
		}
	}

	public static class MandatoryStringHandlerWithDefault extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr, mandatory = true)
		@DefaultToString(DEFAULT_STRING)
		public void spyglassMethod(final String arg1, @UseByte(USE_BYTE_VALUE) byte arg2) {}

		public MandatoryStringHandlerWithDefault(final Context context) {
			super(context);
		}
	}

	public static class HandlerTypeMismatch extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		public void spyglassMethod(final boolean arg1, @UseByte(USE_BYTE_VALUE) byte arg2) {}

		public HandlerTypeMismatch(final Context context) {
			super(context);
		}
	}

	public static class DefaultTypeMismatch extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		@DefaultToBoolean(false)
		public void spyglassMethod(final String arg1, @UseByte(USE_BYTE_VALUE) byte arg2) {}

		public DefaultTypeMismatch(final Context context) {
			super(context);
		}
	}

	public static class UseTypeMismatch extends SpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		public void spyglassMethod(final String arg1, @UseByte(USE_BYTE_VALUE) boolean arg2) {}

		public UseTypeMismatch(final Context context) {
			super(context);
		}
	}
}