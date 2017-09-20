package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.specific_flag_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.annotations.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;

public class SpecificFlagHandlerTestTarget extends View {
	private boolean handlerCalled = false;

	public SpecificFlagHandlerTestTarget(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public SpecificFlagHandlerTestTarget(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public SpecificFlagHandlerTestTarget(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public SpecificFlagHandlerTestTarget(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@SpecificFlagHandler(attributeId = R.styleable.SpecificFlagHandlerTestTarget_specificFlagHandlerAttr, handledFlags = 1)
	public void handlerMethod() {
		handlerCalled = true;
	}

	public boolean wasHandlerCalled() {
		return handlerCalled;
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(SpecificFlagHandlerTestTarget.class)
				.withStyleableResource(R.styleable.SpecificFlagHandlerTestTarget)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.build()
				.passDataToMethods();
	}
}