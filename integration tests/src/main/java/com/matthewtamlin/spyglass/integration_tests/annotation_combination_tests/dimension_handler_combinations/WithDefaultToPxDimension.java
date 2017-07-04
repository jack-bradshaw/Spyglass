package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.dimension_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.common.annotations.units.DimensionUnit;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class WithDefaultToPxDimension extends DimensionHandlerTestTargetBase {
	public static final int DEFAULT_VALUE_PX = 44;

	public WithDefaultToPxDimension(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToPxDimension(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToPxDimension(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToPxDimension(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@DimensionHandler(attributeId = R.styleable.DimensionHandlerTestTargetBase_dimensionHandlerAttr)
	@DefaultToDimension(value = DEFAULT_VALUE_PX, unit = DimensionUnit.PX)
	public void handlerMethod(final int i) {
		setReceivedValue(ReceivedValue.of(i));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(WithDefaultToPxDimension.class)
				.withStyleableResource(R.styleable.DimensionHandlerTestTargetBase)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.build()
				.passDataToMethods();
	}
}