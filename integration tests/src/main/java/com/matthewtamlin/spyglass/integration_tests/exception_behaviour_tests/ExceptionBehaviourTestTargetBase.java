package com.matthewtamlin.spyglass.integration_tests.exception_behaviour_tests;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class ExceptionBehaviourTestTargetBase extends View {
	public ExceptionBehaviourTestTargetBase(final Context context) {
		super(context);
	}

	public ExceptionBehaviourTestTargetBase(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public ExceptionBehaviourTestTargetBase(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public ExceptionBehaviourTestTargetBase(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
	}
}
