package com.matthewtamlin.spyglass.integration_tests.string_handler;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class StringHandlerTestTargetBase extends View {
	private ReceivedValue<String> receivedValue = ReceivedValue.none();

	public StringHandlerTestTargetBase(final Context context) {
		super(context);
	}

	public StringHandlerTestTargetBase(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public StringHandlerTestTargetBase(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public StringHandlerTestTargetBase(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public ReceivedValue<String> getReceivedValue() {
		return receivedValue;
	}

	protected void setReceivedValue(final ReceivedValue<String> receivedValue) {
		this.receivedValue = receivedValue;
	}
}