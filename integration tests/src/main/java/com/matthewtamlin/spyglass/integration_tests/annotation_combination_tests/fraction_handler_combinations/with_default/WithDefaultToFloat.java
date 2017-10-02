package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.fraction_handler_combinations.with_default;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.fraction_handler_combinations.FractionHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.FractionHandler;


public class WithDefaultToFloat extends FractionHandlerTestTargetBase {
	public static final float DEFAULT_VALUE = 17.0F;

	public WithDefaultToFloat(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToFloat(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToFloat(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToFloat(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@FractionHandler(attributeId = R.styleable.FractionHandlerTestTargetBase_fractionHandlerAttr)
	@DefaultToFloat(DEFAULT_VALUE)
	public void handlerMethod(final float f) {
		setReceivedValue(ReceivedValue.of(f));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		WithDefaultToFloat_SpyglassCompanion
				.builder()
				.withTarget(this)
				.withContext(getContext())
				.withStyleableResource(R.styleable.FractionHandlerTestTargetBase)
				.withAttributeSet(attrs)
				.withDefaultStyleAttribute(defStyleAttr)
				.withDefaultStyleResource(defStyleRes)
				.build()
				.passDataToMethods();
	}
}