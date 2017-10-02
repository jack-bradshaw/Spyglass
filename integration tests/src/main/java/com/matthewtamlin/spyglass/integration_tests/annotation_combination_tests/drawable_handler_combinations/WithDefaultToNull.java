package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.drawable_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.DrawableHandler;

public class WithDefaultToNull extends DrawableHandlerTestTargetBase {
	public WithDefaultToNull(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToNull(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToNull(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToNull(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@DrawableHandler(attributeId = R.styleable.DrawableHandlerTestTargetBase_drawableHandlerAttr)
	@DefaultToNull
	public void handlerMethod(final Drawable d) {
		setReceivedValue(ReceivedValue.of(d));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		WithDefaultToNull_SpyglassCompanion
				.builder()
				.withTarget(this)
				.withContext(getContext())
				.withStyleableResource(R.styleable.DrawableHandlerTestTargetBase)
				.withAttributeSet(attrs)
				.withDefaultStyleAttribute(defStyleAttr)
				.withDefaultStyleResource(defStyleRes)
				.build()
				.passDataToMethods();
	}
}