package com.matthewtamlin.spyglass.integration_tests.boolean_handler_test_target;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.ReceivedValue;

public class WithDefaultToBooleanResource extends BooleanHandlerTestTargetBase {
	private ReceivedValue<Boolean> receivedValue = ReceivedValue.none();

	public WithDefaultToBooleanResource(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToBooleanResource(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToBooleanResource(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToBooleanResource(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@BooleanHandler(attributeId = R.styleable.BooleanHandlerTestTargetBase_booleanHandlerAttr)
	@DefaultToBooleanResource(resId = R.bool.BooleanForTesting)
	public void handlerMethod(final boolean b) {
		receivedValue = ReceivedValue.of(b);
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(WithDefaultToBooleanResource.class)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.withStyleableResource(R.styleable.BooleanHandlerTestTargetBase)
				.build()
				.passDataToMethods();
	}

	@Override
	public ReceivedValue<Boolean> getReceivedValue() {
		return receivedValue;
	}
}