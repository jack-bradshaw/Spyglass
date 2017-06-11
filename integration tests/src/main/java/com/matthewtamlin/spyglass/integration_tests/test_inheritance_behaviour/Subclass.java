package com.matthewtamlin.spyglass.integration_tests.test_inheritance_behaviour;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

public class Subclass extends Superclass {
	public Subclass(final Context context) {
		super(context);
	}

	public Subclass(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public Subclass(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public Subclass(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
}