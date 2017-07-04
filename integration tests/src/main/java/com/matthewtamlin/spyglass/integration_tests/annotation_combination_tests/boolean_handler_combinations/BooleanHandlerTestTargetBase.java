package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.boolean_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public abstract class BooleanHandlerTestTargetBase extends View {
	private ReceivedValue<Boolean> receivedValue = ReceivedValue.none();

	public BooleanHandlerTestTargetBase(final Context context) {
		super(context);
	}

	public BooleanHandlerTestTargetBase(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public BooleanHandlerTestTargetBase(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public BooleanHandlerTestTargetBase(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public ReceivedValue<Boolean> getReceivedValue() {
		return receivedValue;
	}

	protected void setReceivedValue(final ReceivedValue<Boolean> receivedValue) {
		this.receivedValue = receivedValue;
	}
}