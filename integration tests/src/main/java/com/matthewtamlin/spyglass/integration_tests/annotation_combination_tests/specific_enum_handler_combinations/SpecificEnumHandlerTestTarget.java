package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.specific_enum_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.markers.annotations.call_handler_annotations.SpecificEnumHandler;

public class SpecificEnumHandlerTestTarget extends View {
	private boolean handlerCalled = false;

	public SpecificEnumHandlerTestTarget(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public SpecificEnumHandlerTestTarget(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public SpecificEnumHandlerTestTarget(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public SpecificEnumHandlerTestTarget(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@SpecificEnumHandler(attributeId = R.styleable.SpecificEnumHandlerTestTarget_specificEnumHandlerAttr, handledOrdinal = 0)
	public void handlerMethod() {
		handlerCalled = true;
	}

	public boolean wasHandlerCalled() {
		return handlerCalled;
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		SpecificEnumHandlerTestTarget_SpyglassCompanion
				.builder()
				.withTarget(this)
				.withContext(getContext())
				.withStyleableResource(R.styleable.SpecificEnumHandlerTestTarget)
				.withAttributeSet(attrs)
				.withDefaultStyleAttribute(defStyleAttr)
				.withDefaultStyleResource(defStyleRes)
				.build()
				.passDataToMethods();
	}
}