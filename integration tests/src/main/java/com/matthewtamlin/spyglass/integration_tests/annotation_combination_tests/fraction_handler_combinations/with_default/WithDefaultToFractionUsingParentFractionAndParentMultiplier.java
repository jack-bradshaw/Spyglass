package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.fraction_handler_combinations.with_default;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.fraction_handler_combinations.FractionHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class WithDefaultToFractionUsingParentFractionAndParentMultiplier extends FractionHandlerTestTargetBase {
	public static final int MULTIPLIER = 44;

	public WithDefaultToFractionUsingParentFractionAndParentMultiplier(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToFractionUsingParentFractionAndParentMultiplier(final Context context,
			final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToFractionUsingParentFractionAndParentMultiplier(final Context context, final AttributeSet attrs,
			final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToFractionUsingParentFractionAndParentMultiplier(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@FractionHandler(attributeId = R.styleable.FloatHandlerTestTargetBase_floatHandlerAttr)
	@DefaultToFractionResource(resId = R.fraction.parent_fraction_for_testing, parentMultiplier = MULTIPLIER)
	public void handlerMethod(final float f) {
		setReceivedValue(ReceivedValue.of(f));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(WithDefaultToFractionUsingParentFractionAndParentMultiplier.class)
				.withStyleableResource(R.styleable.FractionHandlerTestTargetBase)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.build()
				.passDataToMethods();
	}
}