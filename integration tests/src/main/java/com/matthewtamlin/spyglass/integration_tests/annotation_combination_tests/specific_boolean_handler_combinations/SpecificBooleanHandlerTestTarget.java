package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.specific_boolean_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.markers.annotations.call_handler_annotations.SpecificBooleanHandler;

public class SpecificBooleanHandlerTestTarget extends View {
	private boolean handlerCalled = false;

	public SpecificBooleanHandlerTestTarget(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public SpecificBooleanHandlerTestTarget(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public SpecificBooleanHandlerTestTarget(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public SpecificBooleanHandlerTestTarget(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@SpecificBooleanHandler(
			attributeId = R.styleable.SpecificFlagHandlerTestTarget_specificFlagHandlerAttr,
			handledBoolean = true)
	public void handlerMethod() {
		handlerCalled = true;
	}

	public boolean wasHandlerCalled() {
		return handlerCalled;
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		SpecificBooleanHandlerTestTarget_SpyglassCompanion
				.builder()
				.withTarget(this)
				.withContext(getContext())
				.withStyleableResource(R.styleable.SpecificBooleanHandlerTestTarget)
				.withAttributeSet(attrs)
				.withDefaultStyleAttribute(defStyleAttr)
				.withDefaultStyleResource(defStyleRes)
				.build()
				.passDataToMethods();
	}
}