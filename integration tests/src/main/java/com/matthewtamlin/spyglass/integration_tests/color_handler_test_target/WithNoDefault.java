package com.matthewtamlin.spyglass.integration_tests.color_handler_test_target;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.ReceivedValue;

public class WithNoDefault extends ColorHandlerTestTargetBase {
	public WithNoDefault(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithNoDefault(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithNoDefault(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@RequiresApi(21)
	@TargetApi(21)
	public WithNoDefault(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@ColorHandler(attributeId = R.styleable.ColorHandlerTestTargetBase_colorHandlerAttr)
	public void handlerMethod(final int i) {
		setReceivedValue(ReceivedValue.of(i));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(WithNoDefault.class)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.withStyleableResource(R.styleable.ColorHandlerTestTargetBase)
				.build()
				.passDataToMethods();
	}
}