package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.use_annotation_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import java.util.List;

public abstract class UseAnnotationsTestTargetBase extends View {
	private ReceivedValue<List<Object>> receivedValue = ReceivedValue.none();

	public UseAnnotationsTestTargetBase(final Context context) {
		super(context);
	}

	public UseAnnotationsTestTargetBase(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public UseAnnotationsTestTargetBase(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public UseAnnotationsTestTargetBase(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public ReceivedValue<List<Object>> getReceivedValues() {
		return receivedValue;
	}

	public void setReceivedValue(final ReceivedValue<List<Object>> receivedValue) {
		this.receivedValue = receivedValue;
	}

	public abstract ReceivedValue<List<Object>> getExpectedReceivedValues();
}