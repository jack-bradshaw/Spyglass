package com.matthewtamlin.spyglass.integration_tests.enum_constant_handler;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.integration_tests.enum_constant_handler.EnumForTesting.Fruit;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class EnumConstantHandlerTestTargetBase extends View {
	private ReceivedValue<Fruit> receivedValue = ReceivedValue.none();

	public EnumConstantHandlerTestTargetBase(final Context context) {
		super(context);
	}

	public EnumConstantHandlerTestTargetBase(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public EnumConstantHandlerTestTargetBase(final Context context, final AttributeSet attrs,
			final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public EnumConstantHandlerTestTargetBase(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public ReceivedValue<Fruit> getReceivedValue() {
		return receivedValue;
	}

	protected void setReceivedValue(final ReceivedValue<Fruit> receivedValue) {
		this.receivedValue = receivedValue;
	}
}