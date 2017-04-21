package com.matthewtamlin.spyglass.library_tests.core;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.library.use_annotations.UseByte;
import com.matthewtamlin.spyglass.library.value_handler_annotations.StringHandler;

import static com.matthewtamlin.spyglass.library_tests.R.styleable.SpyglassTestView_string_attr;

public class TargetViews {
	public static final String DEFAULT_STRING = "default string";

	public static final byte USE_BYTE_VALUE = 54;

	public static class ObservableSpyglassTestView extends SpyglassTestView {
		private Object[] argsFromLastSpyglassMethodInvocation;

		public ObservableSpyglassTestView(final Context context) {
			super(context);
		}

		public Object[] getArgsFromLastSpyglassMethodInvocation() {
			return argsFromLastSpyglassMethodInvocation;
		}

		public void setArgsFromLastSpyglassMethodInvocation(final Object[] args) {
			this.argsFromLastSpyglassMethodInvocation = args;
		}
	}

	public static class NoAnnotations extends ObservableSpyglassTestView {
		public void spyglassMethod(final String arg1, byte arg2) {
			setArgsFromLastSpyglassMethodInvocation(new Object[]{arg1, arg2});
		}

		public NoAnnotations(final Context context) {
			super(context);
		}
	}

	public static class OnlyHandlerPresent extends ObservableSpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		public void spyglassMethod(final String arg1, @UseByte(USE_BYTE_VALUE) byte arg2) {
			setArgsFromLastSpyglassMethodInvocation(new Object[]{arg1, arg2});
		}

		public OnlyHandlerPresent(final Context context) {
			super(context);
		}
	}

	public static class HandlerAndDefaultPresent extends ObservableSpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		@DefaultToString(DEFAULT_STRING)
		public void spyglassMethod(final String arg1, @UseByte(USE_BYTE_VALUE) byte arg2) {
			setArgsFromLastSpyglassMethodInvocation(new Object[]{arg1, arg2});
		}

		public HandlerAndDefaultPresent(final Context context) {
			super(context);
		}
	}

	public static class HandlerTypeMismatch extends ObservableSpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		public void spyglassMethod(final boolean arg1, @UseByte(USE_BYTE_VALUE) byte arg2) {
			setArgsFromLastSpyglassMethodInvocation(new Object[]{arg1, arg2});
		}

		public HandlerTypeMismatch(final Context context) {
			super(context);
		}
	}

	public static class DefaultTypeMismatch extends ObservableSpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		@DefaultToBoolean(false)
		public void spyglassMethod(final String arg1, @UseByte(USE_BYTE_VALUE) byte arg2) {
			setArgsFromLastSpyglassMethodInvocation(new Object[]{arg1, arg2});
		}

		public DefaultTypeMismatch(final Context context) {
			super(context);
		}
	}

	public static class UseTypeMismatch extends ObservableSpyglassTestView {
		@StringHandler(attributeId = SpyglassTestView_string_attr)
		public void spyglassMethod(final String arg1, @UseByte(USE_BYTE_VALUE) boolean arg2) {
			setArgsFromLastSpyglassMethodInvocation(new Object[]{arg1, arg2});
		}

		public UseTypeMismatch(final Context context) {
			super(context);
		}
	}
}