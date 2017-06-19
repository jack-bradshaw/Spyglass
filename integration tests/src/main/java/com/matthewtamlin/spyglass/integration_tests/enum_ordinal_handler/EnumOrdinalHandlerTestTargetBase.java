package com.matthewtamlin.spyglass.integration_tests.enum_ordinal_handler;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class EnumOrdinalHandlerTestTargetBase extends View {
	private ReceivedValue<Integer> receivedValue = ReceivedValue.none();

	public EnumOrdinalHandlerTestTargetBase(final Context context) {
		super(context);
	}

	public EnumOrdinalHandlerTestTargetBase(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public EnumOrdinalHandlerTestTargetBase(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr) {

		super(context, attrs, defStyleAttr);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public EnumOrdinalHandlerTestTargetBase(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public ReceivedValue<Integer> getReceivedValue() {
		return receivedValue;
	}

	protected void setReceivedValue(final ReceivedValue<Integer> receivedValue) {
		this.receivedValue = receivedValue;
	}
}