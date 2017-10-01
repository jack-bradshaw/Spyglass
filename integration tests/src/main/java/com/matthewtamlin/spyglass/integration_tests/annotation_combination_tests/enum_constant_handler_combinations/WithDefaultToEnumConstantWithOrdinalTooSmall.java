package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.enum_constant_handler_combinations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.enum_constant_handler_combinations.EnumForTesting.Fruit;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.EnumConstantHandler;

public class WithDefaultToEnumConstantWithOrdinalTooSmall extends EnumConstantHandlerTestTargetBase {
	public WithDefaultToEnumConstantWithOrdinalTooSmall(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToEnumConstantWithOrdinalTooSmall(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToEnumConstantWithOrdinalTooSmall(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr) {

		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToEnumConstantWithOrdinalTooSmall(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@EnumConstantHandler(
			attributeId = R.styleable.EnumConstantHandlerTestTargetBase_fruit,
			enumClass = Fruit.class)
	@DefaultToEnumConstant(enumClass = Fruit.class, ordinal = -1)
	public void handlerMethod(final Fruit fruit) {
		setReceivedValue(ReceivedValue.of(fruit));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		WithDefaultToEnumConstantWithOrdinalTooSmall_SpyglassCompanion
				.builder()
				.withTarget(this)
				.withContext(getContext())
				.withStyleableResource(R.styleable.EnumConstantHandlerTestTargetBase)
				.withAttributeSet(attrs)
				.withDefaultStyleAttribute(defStyleAttr)
				.withDefaultStyleResource(defStyleRes)
				.build()
				.passDataToMethods();
	}
}