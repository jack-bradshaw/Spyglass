package com.matthewtamlin.spyglass.library_tests.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class SpyglassTestView extends View {
	public SpyglassTestView(final Context context) {
		super(context);
	}

	public SpyglassTestView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public SpyglassTestView(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr) {

		super(context, attrs, defStyleAttr);
	}

	@RequiresApi(21) // For caller
	@TargetApi(21) // For lint
	public SpyglassTestView(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
	}
}