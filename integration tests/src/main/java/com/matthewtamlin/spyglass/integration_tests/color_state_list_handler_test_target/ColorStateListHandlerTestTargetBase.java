package com.matthewtamlin.spyglass.integration_tests.color_state_list_handler_test_target;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public abstract class ColorStateListHandlerTestTargetBase extends View {
	private ReceivedValue<ColorStateList> receivedValue;

	public ColorStateListHandlerTestTargetBase(final Context context) {
		super(context);
	}

	public ColorStateListHandlerTestTargetBase(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public ColorStateListHandlerTestTargetBase(final Context context, final AttributeSet attrs,
			final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public ColorStateListHandlerTestTargetBase(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public ReceivedValue<ColorStateList> getReceivedValue() {
		return receivedValue;
	}

	protected void setReceivedValue(final ReceivedValue<ColorStateList> receivedValue) {
		this.receivedValue = receivedValue;
	}
}