package com.matthewtamlin.spyglass.integration_tests.exception_behaviour_tests;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;

@SuppressWarnings({"ThrowableInstanceNeverThrown", "UnusedParameters"})
public class ThrowsThrowable extends ExceptionBehaviourTestTargetBase {
	private static final Throwable THROWABLE = new Throwable("test");

	public ThrowsThrowable(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public ThrowsThrowable(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public ThrowsThrowable(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public ThrowsThrowable(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	public static Throwable getExpectedThrowable() {
		return THROWABLE;
	}

	@StringHandler(attributeId = R.styleable.ExceptionBehaviourTestTargetBase_exceptionAttr)
	@DefaultToString("test string")
	public void handlerMethod(final String s) throws Throwable {
		throw THROWABLE;
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(ThrowsThrowable.class)
				.withStyleableResource(R.styleable.ExceptionBehaviourTestTargetBase)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.build()
				.passDataToMethods();
	}
}