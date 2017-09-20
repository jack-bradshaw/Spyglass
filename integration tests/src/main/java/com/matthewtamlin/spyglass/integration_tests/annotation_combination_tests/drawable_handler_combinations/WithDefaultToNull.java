package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.drawable_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.annotations.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

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
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(WithDefaultToNull.class)
				.withStyleableResource(R.styleable.DrawableHandlerTestTargetBase)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.build()
				.passDataToMethods();
	}
}